package org.rina.controller;

import java.util.Optional;

import org.rina.dto.request.PersonneContactDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.dto.response.PersonneContactResponseDto;
import org.rina.model.PersonneContact;
import org.rina.model.Resident;
import org.rina.model.User;
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
@RequestMapping("/personne-contact")
public class PersonneContactController {

    @Autowired
    private PersonneContactServices personneConService;
    @Autowired
    private ResidentServices residentService;
    @Autowired
    private UserService userService;

    /**
     * Récupérer une personne contact par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonneContactResponseDto> getPersonneContactById(@PathVariable Long id) {
        // Chercher la personne de contact par son ID
        Optional<PersonneContact> personC = personneConService.findById(id);
        if (personC.isPresent()) {
           
        	// Si elle existe, la mapper en un objet DTO 
            PersonneContactResponseDto responseDto = new PersonneContactResponseDto(personC.get());
            // Renvoyer une réponse avec la personne de contact 
            return ResponseEntity.ok(responseDto);
        } 
        else {
            // Renvoyer une réponse 200 si la personne de contact n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Créer une nouvelle personne contact.
     */
    @PostMapping("/{idResid}")
    public ResponseEntity<MessageResponseDto> createPersonneContact(@PathVariable Long idResid, @Valid @RequestBody PersonneContactDto personDto) {
        // Vérifier d'abord si le résident existe en fonction de l'ID
        Optional<Resident> resid = residentService.findById(idResid);
        if (resid.isPresent()) {
            
        	// Vérifier si le résident à lier a déjà atteint le nombre maximal de personnes de contact (5)
            int nbPersonneContact = residentService.countPersonContactByResid(idResid);
            if (nbPersonneContact == 5) {
            	String msg = "Le résident a déjà atteint le nombre maximal de personnes de contact";
            	return ResponseEntity.ok(new MessageResponseDto(msg));
            }

            // Vérifier si une personne a un compte utilisateur existant avec l'email
            Optional<User> checkUser = userService.findByUsername(personDto.getEmail());
            if (checkUser.isPresent()) {
            	if(personneConService.existByNamesAndDate(personDto.getNom(), personDto.getPrenom(), personDto.getDateNaissance())) {
            		User user = checkUser.get();
	                // Créer et insérer la personne de contact avec son compte lié
	                PersonneContact newPersonneC = personneConService.insert(personDto.toPersonneContact(user));
	                // Lier la personne de contact au résident
	                residentService.addPersonneContactToResident(idResid, newPersonneC.getId());
            	}
            	else{
                    // Renvoyer une réponse 200 si le résident n'existe pas
                	String msg = "L'adresse email est deja utilisé par une autre personne";
                	return ResponseEntity.ok(new MessageResponseDto(msg));
            	}
            } 
            else {
                // Créer et insérer la personne de contact sans son compte utilisateur
                PersonneContact newPersonneC =  personneConService.insert(personDto.toPersonneContact());
                // Lier la personne de contact au résident
                residentService.addPersonneContactToResident(idResid, newPersonneC.getId());
            }
            // Renvoie la réponse HTTP indiquant le succès de l'opération
            String msg = "Personne de contact ajoutée avec succès";
        	return ResponseEntity.ok(new MessageResponseDto(msg));
        }
        else {
            // Renvoyer une réponse 200 si le résident n'existe pas
        	String msg = "Le résident n'existe pas";
        	return ResponseEntity.ok(new MessageResponseDto(msg));
        }
    }

    /**
     * Mettre à jour une personne contact existante.
    */
    @PutMapping("/{id}")
    public ResponseEntity<PersonneContactResponseDto> updatePersonneContact(@PathVariable Long id, @Valid @RequestBody PersonneContactDto personDto) {
        // Vérifier d'abord si la personne de contact existe en fonction de l'ID
        Optional<PersonneContact> existingPersonneC = personneConService.findById(id);
        if (existingPersonneC.isPresent()) {
            // Mise à jour de l'ID avec la valeur fournie
            personDto.setId(id);

            // Vérifier si la personne a un compte utilisateur existant
            Optional<User> checkUser = userService.findByUsername(personDto.getEmail());
            if (checkUser.isPresent()) {
                User user = checkUser.get();
                // Mise à jour de la personne de contact existante avec les nouvelles valeurs et le compte utilisateur lié
                PersonneContact updatePersonC = personneConService.updatePersonneContact(id, personDto.toPersonneContact(user));
                // Mapper la personne de contact mise à jour en un objet DTO et la renvoyer en réponse
                PersonneContactResponseDto responseDto = new PersonneContactResponseDto(updatePersonC);
                return ResponseEntity.ok(responseDto);
            } 
            else {
                // Mise à jour de la personne de contact existante avec les nouvelles valeurs sans le compte utilisateur lié
                PersonneContact updatePersonC = personneConService.updatePersonneContact(id, personDto.toPersonneContact());
                // Mapper la personne de contact mise à jour en un objet DTO et la renvoyer en réponse
                PersonneContactResponseDto responseDto = new PersonneContactResponseDto(updatePersonC);
                return ResponseEntity.ok(responseDto);
            }
        }
        // Renvoyer une réponse 200 si la personne de contact n'existe pas
        return ResponseEntity.ok().build();
    }

    /**
     * Supprimer une personne contact par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deletePersonneContact(@PathVariable Long id) {
        if (personneConService.existsById(id)) {
            // Utilisez cette méthode pour supprimer le lien avec les résidents dans la table de liaison
            residentService.removePersonneContactFromResident(id);

            // Supprime ensuite la personne de contact elle-même
            personneConService.deleteById(id);
            // Renvoie une réponse HTTP indiquant le succès de l'opération
            String msg = "Personne de contact supprimée avec succès"; 
            return ResponseEntity.ok(new MessageResponseDto(msg));
        } 
        else {
            // Renvoyer une réponse 200 si la personne de contact n'existe pas
        	 String msg = "La personne de contact n'existe pas"; 
             return ResponseEntity.ok(new MessageResponseDto(msg));
        }
    }
}