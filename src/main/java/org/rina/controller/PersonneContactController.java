package org.rina.controller;

import org.rina.dto.request.PersonneContactDto;
import org.rina.model.PersonneContact;
import org.rina.model.Resident;
import org.rina.model.User;
import org.rina.service.PersonneContactServices;
import org.rina.service.ResidentServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
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
	 * Récupérer un personne contact par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PersonneContact> getPersonneContactById(@PathVariable Long id) {
		Optional<PersonneContact> personC = personneConService.findById(id);
		
		if (personC.isPresent()) {
			return ResponseEntity.ok(personC.get());
		}

		else return ResponseEntity.notFound().build();
	}

    /**
	 * Créer une nouvelle personne contact.
	 */
    @PostMapping("/{id}")
    public ResponseEntity<String> createPersonneContact(@PathVariable Long id, @Valid @RequestBody PersonneContactDto personDto) {
    	//Vérifie que le résident existe 
    	Optional<Resident> resid = residentService.findById(id); 
    	
    	if (resid.isPresent()) {
	    	// Vérifie si le résident à lier a déjà atteint le nombre maximal de personnes de contact (5)
	    	int nbPersonneContact = residentService.countPersonContactByResid(id);
	    	if (nbPersonneContact == 5) {
	            return ResponseEntity.badRequest().body("Le résident a déjà atteint le nombre maximal de personnes de contact.");
	        }
	    	//Vérifie si la personne a un compte 
	    	Optional<User> checkUser = userService.findByUsername(personDto.getEmail());
	    	
	    	if (checkUser.isPresent()) {
	    		User user = checkUser.get();
		    	//Crée et insère la personne de contact avec son compte lié
		    	PersonneContact newPersonneC = personDto.toPersonneContact(user);
				personneConService.insert(newPersonneC);
				
				if (newPersonneC != null) {
					//Lie la personne de contact au résident 
			    	residentService.addPersonneContactToResident(id, newPersonneC.getId());
				}
	    	}
	    	else {
	    		// Crée et insère la personne de contact sans son compte 
		    	PersonneContact newPersonneC = personDto.toPersonneContact();
				personneConService.insert(newPersonneC);
				
				if (newPersonneC != null) {
				//Lie la personne de contact au résident 
					log.info("id pc: " +  newPersonneC.getId());
		    	residentService.addPersonneContactToResident(id, newPersonneC.getId());
				}
	    	}
	    	
			// Renvoie la réponse ok
			return ResponseEntity.ok().build();
    	}  
    	
    	else return ResponseEntity.notFound().build();
    }

    /**
	 * Mettre à jour une personne contact existante.
	 */
    @PutMapping("/{id}")
    public ResponseEntity<PersonneContact> updatePersonneContact(@PathVariable Long id, @Valid @RequestBody PersonneContactDto personDto) {
    	// Vérifie d'abord si la personne existe en fonction de l'ID
    	Optional<PersonneContact> existingPersonneC = personneConService.findById(id);

        if (existingPersonneC.isPresent()) {
        	//Mise à jour de l'id
        	personDto.setId(id);
        	//Vérifie si la personne a un compte 
	    	Optional<User> checkUser = userService.findByUsername(personDto.getEmail());
	    	if (checkUser.isPresent()) {
	    		User user = checkUser.get();
	    		//Mise à jour de la personne existante avec les nouvelles valeurs
	    		PersonneContact updatePersonC = personneConService.updatePersonneContact(id, personDto.toPersonneContact(user));
	    		// Renvoie la réponse avec le résident mis à jour
				return ResponseEntity.ok(updatePersonC);
	    	}
	    	else {
		    	//Mise à jour de la personne existante avec les nouvelles valeurs
	    		PersonneContact updatePersonC = personneConService.updatePersonneContact(id, personDto.toPersonneContact());
	    		// Renvoie la réponse avec le résident mis à jour
	    		return ResponseEntity.ok(updatePersonC);
	    	}
        }
        return ResponseEntity.notFound().build();
    }

    /**
	 * Supprimer une personne contact par son ID.
	 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonneContact(@PathVariable Long id) {
    	 if (personneConService.existsById(id)) {
             //Utilisez cette methode pour supprimer le lien avec les residents dans la table de liaison
             residentService.removePersonneContactFromResident(id);
             
             //Supprime ensuite la personne de contact elle-même
             personneConService.deleteById(id);
             return ResponseEntity.ok().build();
         }
    	 else return ResponseEntity.notFound().build();
     }
    
}