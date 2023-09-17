package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;

import org.rina.dto.request.EvenementDto;
import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.rina.service.EtablissementServices;
import org.rina.service.EvenementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evenement")
public class EvenementController {

	@Autowired
	private EvenementServices evenementService;
	@Autowired
	private EtablissementServices etablissementService;

	/**
     * Récupérer tous les évenements.
     */
	@GetMapping
	public List<Evenement> getAllEvents() {
		return evenementService.findAll();
	}

	/**
     * Récupérer un évenement par son ID.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Evenement> getEventById(@PathVariable Long id) {
		Optional<Evenement> evenement = evenementService.findById(id);
		if (evenement.isPresent()) {
			return ResponseEntity.ok(evenement.get());
		}
		return ResponseEntity.notFound().build();
	}

	/**
     * Créer un nouveau évenement.
     */
	@PostMapping
	public ResponseEntity<Evenement> createEvent(@Valid @RequestBody EvenementDto evenementDto) {
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		evenementService.insert(evenementDto.toEvenement(etab));
		return ResponseEntity.ok().build();
	}

	/**
     * Mettre à jour un évenement existant.
     */
	@PutMapping("/{id}")
	public ResponseEntity<Evenement> updateEvent(@PathVariable Long id, @Valid @RequestBody EvenementDto evenementDto) {
		// Vérifie d'abord si l'événement existe en fonction de l'ID
		Optional<Evenement> existingEvenement = evenementService.findById(id);

		if (existingEvenement.isPresent()) {
	    	Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Mise à jour l'événement existant avec les nouvelles valeurs
	        evenementDto.setId(id);
	        return ResponseEntity.ok(evenementService.updateEvenement(id, evenementDto.toEvenement(etab)));
	    }
	    
	    else return ResponseEntity.notFound().build();	
	}

	/**
     * Supprimer un évenement par son ID.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		if (evenementService.existsById(id)) {
			evenementService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}