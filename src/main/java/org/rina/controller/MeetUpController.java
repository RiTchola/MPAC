package org.rina.controller;

import java.util.List;
import java.util.Optional;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MeetUpDto;
import org.rina.enums.Roles;
import org.rina.model.Etablissement;
import org.rina.model.MeetUp;
import org.rina.model.PersonneContact;
import org.rina.service.EtablissementServices;
import org.rina.service.MeetUpServices;
import org.rina.service.PersonneContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/meetup")
public class MeetUpController {

    @Autowired
    private MeetUpServices meetUpService;
    @Autowired
    private EtablissementServices etablissementService;
    @Autowired
   	private PersonneContactServices persCService;

    /**
     * Récupérer tous les meetUps de l'établissement.
     */
    @GetMapping
    public ResponseEntity<List<MeetUp>> getAllMeetUps() {
        List<MeetUp> meetUps =  meetUpService.findAllMeetOrderByDateDesc();
        return ResponseEntity.ok(meetUps);
    }
    
    /**
     * Récupérer tous les meetUps d'une personne.
     */
    @GetMapping("/personneC")
    public ResponseEntity<List<MeetUp>> getAllMeetUpsById(Authentication auth) {
    	
    	if (auth != null || hasRole(auth, Roles.PERSONNECONTACT)) {
	    	//vérifie si la personne  existe
	    	Optional<PersonneContact> existingPersonC = persCService.findByUsername(auth.getName());
		    if ( existingPersonC.isPresent()) {
		    	
		    	//Retourne sa liste de meetUp
		    	PersonneContact personC = existingPersonC.get();
		        List<MeetUp> meetUpsId =  meetUpService.findAllMeetByIdOrderByDateDesc(personC.getId());
		        return ResponseEntity.ok(meetUpsId);
		    }
    	}
	    
    	return ResponseEntity.notFound().build();
    }

    /**
     * Récupérer un meetUp par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MeetUp> getMeetUpById(@PathVariable Long id) {
        Optional<MeetUp> meetUp = meetUpService.findById(id);
        if (meetUp.isPresent()) {
			return ResponseEntity.ok(meetUp.get());
		}
        
        else return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau meetUp.
     */
    @SuppressWarnings("unused")
	@PostMapping
    public ResponseEntity<String> createMeetUp(@Valid @RequestBody MeetUpDto meetUpDto, Authentication auth) {
    	if (auth != null || hasRole(auth, Roles.PERSONNECONTACT)) {
	    	Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));
	    	PersonneContact persC = persCService.findByUsername(auth.getName())
					.orElseThrow(() -> new NotExistException(auth.getName()));
	    	
	    	MeetUp meetUp = meetUpService.insert(meetUpDto.toMeetUp(etab, persC));
	    	return ResponseEntity.ok().build();
    	}
    	
    	else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le rapport existe déjà.");
    }

    /**
     * Mettre à jour un meetUp existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MeetUp> updateMeetUp(@PathVariable Long id, @Valid @RequestBody MeetUpDto meetUpDto, Authentication auth) {
    	if (auth != null || hasRole(auth, Roles.PERSONNECONTACT)) {
	    	// Vérifie d'abord si le meetUp existe en fonction de l'ID
	    	Optional<MeetUp> existingMeetUp = meetUpService.findById(id);
	       
	        if (existingMeetUp.isPresent()) {
	    	   Long idEtab = Long.valueOf(1);
			   Etablissement etab = etablissementService.findById(idEtab)
						.orElseThrow(() -> new NotExistException(idEtab.toString()));
		       PersonneContact persC = persCService.findByUsername(auth.getName())
						.orElseThrow(() -> new NotExistException(auth.getName()));
		  
		       // Mise à jour du meetUp existant avec les nouvelles valeurs
		        meetUpDto.setId(id);
		        MeetUp updateMeetUp = meetUpService.updateMeetUp(id, meetUpDto.toMeetUp(etab, persC));
		        
		        //Renvoie la réponse avec le meet mis à jour
		        return ResponseEntity.ok(updateMeetUp);
		    }
	     }
    	
       return ResponseEntity.notFound().build();
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