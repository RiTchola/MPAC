package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.ResidentDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	private PersonneContactServices personneConService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * Récupérer tous les résidents soit de l'établissement, soit d'une personne de contact.
	 */
	@GetMapping
	public ResponseEntity<List<Resident>> getAllResidents(Authentication auth) {
		if (hasRole(auth, Roles.PERSONNECONTACT) ) {
			//Obtient le nom d'utilisateur de la personne connectée
		    String username = auth.getName();
	    	//Recherche la personne de contact
		    PersonneContact persC = personneConService.findByUsername(username)
		    		.orElseThrow(() -> new NotExistException(username));
		 
		    // Récupérez les données de la personne de contact pour trouver ses résidents
		    String nomPerson = persC.getNom();
		    String prenomPerson = persC.getPrenom();
		    LocalDate dateBirthPerson = persC.getDateNaissance();
		    
		    //utilise les champs pour récupérer les résidents de cette personne connecté
		    List<Resident> residsPersonC = residentService.findAllResidToPersonContact(nomPerson, prenomPerson, dateBirthPerson);
		    
		    return ResponseEntity.ok(residsPersonC);	
	    }
		else {
			List<Resident> residents = residentService.findAllResidentOrderByDateDesc();
			return ResponseEntity.ok(residents);
		}
	}

	/**
	 * Récupérer tous les personnes de contacts d'un résident.
	 */
	@GetMapping("/liste/{id}")
	public ResponseEntity<List<PersonneContact>> getAllPersonContactsById(@PathVariable Long id) {
		List<PersonneContact> persCs = personneConService.findAllPersonContactToResid(id);
		return ResponseEntity.ok(persCs);
	}

	/**
	 * Récupérer un résident par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
		Optional<Resident> resident = residentService.findById(id);
		if (resident.isPresent()) {
			return ResponseEntity.ok(resident.get());
		}

		else return ResponseEntity.notFound().build();
	}

	/**
	 * Créer un nouveau résident.
	 */
	@PostMapping
	public ResponseEntity<Resident> createResident(@Valid @RequestBody ResidentDto residDto) {
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));
		MedecinTraitant medecinT = medecinTService.findById(residDto.getMedecinId())
				.orElseThrow(() -> new NotExistException(residDto.getMedecinId().toString()));
		User user = userService.findById(residDto.getUserId())
				.orElseThrow(() -> new NotExistException(residDto.getUserId().toString()));

		// Crée et insère le résident
		Resident newResident = residDto.toResident(user, medecinT, etab);
		residentService.insert(newResident);

		// Renvoie la réponse avec le résident créé et l'ID généré
		return ResponseEntity.ok(newResident);
	}

	/**
	 * Mettre à jour un résident existant.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Resident> updateResident(@PathVariable Long id, @Valid @RequestBody ResidentDto residDto) {
		// Vérifie d'abord si le résident existe en fonction de l'ID
		Optional<Resident> existingResident = residentService.findById(id);

		if (existingResident.isPresent()) {
			Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));
			MedecinTraitant medecinT = medecinTService.findById(residDto.getMedecinId())
					.orElseThrow(() -> new NotExistException(residDto.getMedecinId().toString()));
			User user = userService.findById(residDto.getUserId())
					.orElseThrow(() -> new NotExistException(residDto.getUserId().toString()));

			// Mise à jour du résident existant avec les nouvelles valeurs
			residDto.setId(id);
			Resident updateResident = residentService.updateResident(id, residDto.toResident(user, medecinT, etab));

			// Renvoie la réponse avec le résident mis à jour
			return ResponseEntity.ok(updateResident);
		}

		else return ResponseEntity.notFound().build();
	}

	/**
	 * Désactiver un résident par son ID.
	 */
	@DeleteMapping("/{id}")
	ResponseEntity<String> disableResidentById(@PathVariable Long id) {
		Optional<Resident> resident = residentService.findById(id);

		if (resident.isPresent()) {
			Resident outResident = resident.get();
			
			// Désactive le compte utilisateur 
	        User user = outResident.getUser();
	        user.setEnabled(false);
	        userService.insert(user); 
	        
	        // Ensuite, sauvegarde les modifications du résident
	        residentService.insert(outResident);
	        return ResponseEntity.ok().build();
		}
		else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le compte n'existe pas.");
	}
	
	/**
	 * Petite méthode privée qui vérifie si l'objet auth possède le role désigné
	 * 
	 * @param auth
	 * @param role
	 * @return vrai s'il possède le role
	 */
	private boolean hasRole(Authentication auth, Roles role) {
		String roleStr = role.name();
		return auth.getAuthorities().stream().anyMatch(a -> roleStr.equals(a.getAuthority()));
	}
	
	
}