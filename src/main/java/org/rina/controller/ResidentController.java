package org.rina.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.ResidentDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.dto.response.PersonneContactResponseDto;
import org.rina.dto.response.ResidentResponseDto;
import org.rina.enums.Roles;
import org.rina.model.Etablissement;
import org.rina.model.MedecinTraitant;
import org.rina.model.PersonneContact;
import org.rina.model.Resident;
import org.rina.model.User;
import org.rina.service.EtablissementServices;
import org.rina.service.MedecinTraitantServices;
import org.rina.service.PersonneContactServices;
import org.rina.service.ResidentServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    private ResidentServices residentService;
    @Autowired
    private EtablissementServices etablissementService;
    @Autowired
    private MedecinTraitantServices medecinTService;
    @Autowired
    private PersonneContactServices persCService;
    @Autowired
    private UserService userService;

    /**
     * Récupérer tous les résidents soit de l'établissement, soit d'une personne de contact.
     */
    @GetMapping("/listeResident/{username}")
    public ResponseEntity<List<ResidentResponseDto>> getAllResidents(@PathVariable String username) {
        // Vérifier l'existence de l'utilisateur par son nom d'utilisateur
        Optional<User> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            
            // Vérifier le rôle de l'utilisateur
            if (user.getRole() == Roles.PERSONNECONTACT) {
                // Si l'utilisateur est une personne de contact, Vérifier si la personne existe
                Optional<PersonneContact> existingPersonC = persCService.findByUsername(username);
                if (existingPersonC.isPresent()) {
                    
                	// Retourner la liste des résidents associés à la personne de contact
                    PersonneContact persC = existingPersonC.get();
                    List<Resident> residsPersonC = residentService.findAllResidToPersonContact(
                            persC.getNom(), persC.getPrenom(), persC.getDateNaissance());

                    List<ResidentResponseDto> responseDtos = residsPersonC.stream()
                            .map(ResidentResponseDto::new)
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(responseDtos);
                }
            } 
            else if (user.getRole() == Roles.ETABLISSEMENT || user.getRole() == Roles.ADMIN) {
                // Si l'utilisateur est un établissement ou un administrateur
                List<Resident> residents = residentService.findAllResidentOrderByDateDesc();
                List<ResidentResponseDto> responseDtos = residents.stream()
                        .map(ResidentResponseDto::new)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(responseDtos);
            }
        }
        // Renvoyer une réponse 200 si l'utilisateur ou la personne de contact n'existe pas
        return ResponseEntity.ok().build();
    }

    /**
     * Récupérer tous les personnes de contacts d'un résident.
     */
    @GetMapping("/listePersonneContact/{idResid}")
    public ResponseEntity<List<PersonneContactResponseDto>> getAllPersonContactsById(@PathVariable Long idResid) {
        // Récupérer la liste des personnes de contact pour le résident
        List<PersonneContact> persCs = persCService.findAllPersonContactToResid(idResid);

        // Mapper la liste d'entités en une liste de DTO de réponse
        List<PersonneContactResponseDto> responseDtos = persCs.stream()
                .map(PersonneContactResponseDto::new)
                .collect(Collectors.toList());

        // Retourner la liste de DTOs de réponse
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Récupérer un résident par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        // Vérifier si un résident existe pour l'ID spécifié
        Optional<Resident> resident = residentService.findById(id);
        if (resident.isPresent()) {
            // Renvoyer le résident en réponse
            return ResponseEntity.ok(resident.get());
        } 
        else {
            // Renvoyer une réponse 200 si le résident n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Créer un nouveau résident.
     */
    @PostMapping("/{idMedecin}/{usernameResid}")
    public ResponseEntity<ResidentResponseDto> createResident(@PathVariable("idMedecin") Long idMedecin, @PathVariable("usernameResid") String usernameResid,
            @Valid @RequestBody ResidentDto residDto) {
        Long idEtab = etablissementService.getEtablissementId();
        // Récupérer l'établissement associé au résident
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));
        // Récupérer le médecin traitant associé au résident
        MedecinTraitant medecinT = medecinTService.findById(idMedecin)
                .orElseThrow(() -> new NotExistException(idMedecin.toString()));
        // Récupérer l'utilisateur associé au résident
        User user = userService.findByUsername(usernameResid)
                .orElseThrow(() -> new NotExistException(usernameResid));

        // Remplacer les séparateurs autres que la virgule par des virgules dans les menus
        residDto.setAntChirugical(checkAndReplaceSeparators(residDto.getAntChirugical()));
        residDto.setAntMedical(checkAndReplaceSeparators(residDto.getAntMedical()));
        residDto.setMotifEntree(checkAndReplaceSeparators(residDto.getMotifEntree()));
        residDto.setEtatSante(checkAndReplaceSeparators(residDto.getEtatSante()));
        
        // Créer et insérer le résident dans la base de données
        Resident newResident = residDto.toResident(user, medecinT, etab);
        residentService.insert(newResident);

        // Mapper le résident créé en un objet DTO et le renvoyer en réponse
        ResidentResponseDto responseDto = new ResidentResponseDto(newResident);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Mettre à jour un résident existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponseDto> updateResident(@PathVariable Long id, @Valid @RequestBody ResidentDto residDto) {
        // Vérifier d'abord si le résident existe en fonction de l'ID
        Optional<Resident> existingResident = residentService.findById(id);

        if (existingResident.isPresent()) {
            Resident resident = existingResident.get();
            // Comparez les nouvelles valeurs avec les anciennes.
			/*
			 * if (!resident.getNom().equals(residDto.getNom()) &&
			 * !resident.getPrenom().equals(residDto.getPrenom()) &&
			 * !resident.getDateNaissance().equals(residDto.getDateNaissance())) { // Les
			 * nouvelles valeurs entraîneraient une violation de l'unicité. return
			 * ResponseEntity.ok().build(); }
			 */
       
            // Récupérer l'établissement associé au résident
            Long idEtab = etablissementService.getEtablissementId();
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));
            // Récupérer le médecin traitant associé au résident
            MedecinTraitant medecinT = resident.getMedecinTraitant();
            // Récupérer l'utilisateur associé au résident
            User user = resident.getUser();
            
            // Remplacer les séparateurs autres que la virgule par des virgules dans les menus
            residDto.setAntChirugical(checkAndReplaceSeparators(residDto.getAntChirugical()));
            residDto.setAntMedical(checkAndReplaceSeparators(residDto.getAntMedical()));
            residDto.setMotifEntree(checkAndReplaceSeparators(residDto.getMotifEntree()));
            residDto.setEtatSante(checkAndReplaceSeparators(residDto.getEtatSante()));
            residDto.setMotifSortie(checkAndReplaceSeparators(residDto.getMotifSortie()));

            // Mise à jour du résident existant avec les nouvelles valeurs
            residDto.setId(id);
            Resident updateResident = residentService.updateResident(id, residDto.toResident(user, medecinT, etab));
            // Mapper le résident mis à jour en un objet DTO et le renvoyer en réponse
            ResidentResponseDto responseDto = new ResidentResponseDto(updateResident);
         // Retourner une réponse 200 OK avec le ResidentResponseDto
            return ResponseEntity.ok(responseDto);
        } 
        else {
        	// Retourner une réponse 200 OK avec un corps vide si le menu n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Désactiver un résident par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> disableResidentById(@PathVariable Long id) {
        // Vérifier d'abord si le résident existe en fonction de l'ID
        Optional<Resident> resident = residentService.findById(id);

        if (resident.isPresent()) {
            Resident outResident = resident.get();
            outResident.setActif(false);
            residentService.insert(outResident);
            
            // Désactiver le compte utilisateur associé au résident
            User user = outResident.getUser();
            user.setEnabled(false);
            userService.insert(user);

            // Insèrer le résident dans la base de données
            residentService.insert(outResident);
            // Retourner une réponse 200 OK avec un message indiquant que le compte du résident a été désactivé
            String msg = "Le compte du résident " + outResident.getNom() + " a été désactivé" ;
            return ResponseEntity.ok(new MessageResponseDto(msg));
        } 
        else {
        	// Retourner une réponse 200 OK avec un message indiquant que Le compte n'existe pas
        	String msg = "Le compte n'existe pas";
            return ResponseEntity.ok(new MessageResponseDto(msg));
        }
    }
    
    /**
     * Remplace les séparateurs autres que la virgule par des virgules dans la chaîne de menu.
     */ 
    private String checkAndReplaceSeparators(String input) {
        // Vérifier si la chaîne est nulle ou vide
        if (input == null || input.isEmpty()) {
            return input; // Rien à faire si la chaîne est nulle ou vide
        }
        
        // Remplacer les séparateurs spécifiques (par exemple, ; ! : | / .) par des virgules
        String result = input.replaceAll("[;!:|/.?]+", ",");
        return result;
    }
}