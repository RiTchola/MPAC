package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;

import org.rina.dto.request.ActiviteDto;
import org.rina.model.Activite;
import org.rina.model.Etablissement;
import org.rina.service.ActiviteServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

	@Autowired
	private ActiviteServices activiteService;
	@Autowired
	private EtablissementServices etablissementService;

	/**
     * Récupérer tous les activités.
     */
	@GetMapping
	public ResponseEntity<List<Activite>> getAllActivity() {
		List<Activite> activities = activiteService.findAll();
		return ResponseEntity.ok(activities);
	}

	/**
     * Récupérer une activité par son ID.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Activite> getActivityById(@PathVariable Long id) {
		Optional<Activite> activite = activiteService.findById(id);
		if (activite.isPresent()) {
			return ResponseEntity.ok(activite.get());
		}

		else return ResponseEntity.notFound().build();
	}

	 /**
     * Créer une nouvelle activité.
     */
	@PostMapping
	public ResponseEntity<Activite> createActivity(@Valid @RequestBody ActiviteDto actDto) {
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		// Crée et insère l'activité
		Activite newActivite = actDto.toActivite(etab);
		activiteService.insert(newActivite);
		
		// Renvoie la réponse avec le activité créé et l'ID généré
        return ResponseEntity.ok(newActivite);
	}

	 /**
     * Mettre à jour une activité existante.
     */
	@PutMapping("/{id}")
	public ResponseEntity<Activite> updateActivity(@PathVariable Long id, @Valid @RequestBody ActiviteDto actDto) {
		// Vérifie d'abord si l'activité existe en fonction de l'ID
	    Optional<Activite> existingActivite = activiteService.findById(id);
	    
	    if (existingActivite.isPresent()) {
	    	Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Mise à jour l'activité existante avec les nouvelles valeurs
	        actDto.setId(id);
	        Activite updateActivite = activiteService.updateActivite(id, actDto.toActivite(etab));
	        
	        //Renvoie la réponse avec le activité mis à jour
	        return ResponseEntity.ok(updateActivite);
	    }
	    
	    else return ResponseEntity.notFound().build();	
	}

	/**
     * Supprimer une activité par son ID.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
		if (activiteService.existsById(id)) {
			activiteService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		else return ResponseEntity.notFound().build();
	}
	
}