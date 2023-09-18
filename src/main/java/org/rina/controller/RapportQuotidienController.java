package org.rina.controller;

import org.rina.model.RapportQuotidien;
import org.rina.model.Resident;
import org.rina.service.RapportQuotidienServices;
import org.rina.service.ResidentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.rina.dto.request.RapportQuotidienDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rapport-quotidien")
public class RapportQuotidienController {

    @Autowired
    private RapportQuotidienServices rapportQuotService;
    @Autowired
	private ResidentServices residentService;

    /**
     * Récupérer tous les rapports quotidien.
     */
    @GetMapping
    public ResponseEntity<List<RapportQuotidien>> getAllRapportsQuotidiens() {
        List<RapportQuotidien> rapquotidiens = rapportQuotService.findAll();
        return ResponseEntity.ok(rapquotidiens);
    }

    /**
     * Récupérer un rapport quotidien par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RapportQuotidien> getRapportQuotidienById(@PathVariable Long id) {
        Optional<RapportQuotidien> rapportQuotidien = rapportQuotService.findById(id);
        if (rapportQuotidien.isPresent()) {
            return ResponseEntity.ok(rapportQuotidien.get());
        }
        
        else return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau rapport quotidien.
     */
    @PostMapping
    public ResponseEntity<RapportQuotidien> createRapportQuotidien(@Valid @RequestBody RapportQuotidienDto rapportQuotDto) {
        //Récupere le résident lié
    	Optional<Resident> existingResid = residentService.findById(rapportQuotDto.getResidentId());
    	
    	if (existingResid.isPresent()) {
    		Resident resid = existingResid.get();
    		// Crée et insère le rapport quotidien
    		RapportQuotidien newRapportQ = rapportQuotDto.toRapportQuotidien(resid);
    		rapportQuotService.insert(newRapportQ);
    		
    		// Renvoie la réponse avec le rapport créé et l'ID généré
            return ResponseEntity.ok(newRapportQ);
    	}
    	
    	else return ResponseEntity.notFound().build(); 
    }

    /**
     * Mettre à jour un rapport quotidien existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RapportQuotidien> updateRapportQuotidien(@PathVariable Long id, @Valid @RequestBody RapportQuotidienDto rapportQuotDto) {
    	// Vérifie d'abord si le rapport existe en fonction de l'ID
    	Optional<RapportQuotidien> existingRapport = rapportQuotService.findById(id);
    	Optional<Resident> existingResid = residentService.findById(rapportQuotDto.getResidentId());

        if (existingRapport.isPresent()) {
        	Resident resid = existingResid.get();
        	
        	// Mise à jour du rapport existant avec les nouvelles valeurs
	        rapportQuotDto.setId(id);
	        RapportQuotidien updateRapportQuot = rapportQuotService.updateRapportQuotidien(id, rapportQuotDto.toRapportQuotidien(resid));
	        
	        //Renvoie la réponse avec le rapport mis à jour
	        return ResponseEntity.ok(updateRapportQuot);
        }
        return ResponseEntity.notFound().build();
    }

    
}