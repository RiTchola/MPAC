package org.rina.controller;

import org.rina.dto.request.MedecinTraitantDto;

import org.rina.model.MedecinTraitant;
import org.rina.service.MedecinTraitantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/medecinTraitant")
public class MedecinTraitantController {

	@Autowired
	private MedecinTraitantServices medecinService;

	/**
     * Récupérer un medecinTraitant par son ID.
     */
	@GetMapping("/{id}")
	public ResponseEntity<MedecinTraitant> getmedecinTById(@PathVariable Long id) {
		Optional<MedecinTraitant> medecinT = medecinService.findById(id);
		if (medecinT.isPresent()) {
			return ResponseEntity.ok(medecinT.get());
		}
		
		else return ResponseEntity.notFound().build();
	}

	/**
     * Créer un nouveau medecinTraitant.
     */
	@PostMapping
	public ResponseEntity<MedecinTraitant> createmedecinT(@Valid @RequestBody MedecinTraitantDto medecinDto) {
		//Crée et insère le medecin
		MedecinTraitant newMedecinT = medecinDto.toMedecinTraitant();
		medecinService.insert(newMedecinT);
		
		//Renvoie la réponse avec le medecin créé et l'ID généré
        return ResponseEntity.ok(newMedecinT);
	}

	 /**
     * Mettre à jour un medecinTraitant existant.
     */
	@PutMapping("/{id}")
	public ResponseEntity<MedecinTraitant> updatemedecinT(@PathVariable Long id, @Valid @RequestBody MedecinTraitantDto medecinDto) {
		// Vérifie d'abord si le médecin traitant existe en fonction de l'ID
		Optional<MedecinTraitant> existingMedecinT = medecinService.findById(id);
		
		if (existingMedecinT.isPresent()) {
			
			// Mise à jour du médecin traitant existant avec les nouvelles valeurs
			medecinDto.setId(id);
			MedecinTraitant updateMedecinT = medecinService.updateMedecinTraitant(id, medecinDto.toMedecinTraitant());
			
			//Renvoie la réponse avec le medecin mis à jour
			return ResponseEntity.ok(updateMedecinT);
		}
		
		else return ResponseEntity.notFound().build();	
	}
	
	
}
