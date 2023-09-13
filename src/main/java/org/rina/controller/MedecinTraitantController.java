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

	@GetMapping("/{id}")
	public ResponseEntity<MedecinTraitant> getmedecinTById(@PathVariable Long id) {
		Optional<MedecinTraitant> medecinT = medecinService.findById(id);
		if (medecinT.isPresent()) {
			return ResponseEntity.ok(medecinT.get());
		}
		
		else return ResponseEntity.notFound().build();
	}

	@PostMapping
	public MedecinTraitant createmedecinT(@Valid @RequestBody MedecinTraitantDto medecinDto) {
		return medecinService.insert(medecinDto.toMedecinTraitant());
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedecinTraitant> updatemedecinT(@PathVariable Long id,
			@Valid @RequestBody MedecinTraitantDto medecinDto) {
		Optional<MedecinTraitant> existingMedecinTraitant = medecinService.findById(id);
		
		// Vérifie d'abord si le médecin traitant existe en fonction de l'ID
		if (existingMedecinTraitant.isPresent()) {
			// Mise à jour du médecin traitant existant avec les nouvelles valeurs
			medecinDto.setId(id);
			return ResponseEntity.ok(medecinService.updateMedecinTraitant(id, medecinDto.toMedecinTraitant()));
		}
		
		else return ResponseEntity.notFound().build();	
	}
	
}
