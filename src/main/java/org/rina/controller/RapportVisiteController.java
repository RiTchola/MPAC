package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.RapportVisiteDto;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.service.EtablissementServices;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rapport-visite")
public class RapportVisiteController {

    @Autowired
    private RapportVisiteServices rapportVService;
    @Autowired
	private EtablissementServices etablissementService;

    /**
     * Récupérer tous les rapports de visites.
     */
    @GetMapping
    public ResponseEntity<List<RapportVisite>> getAllRapportVisite() {
        List<RapportVisite> rapVisites = rapportVService.findAll();
        return ResponseEntity.ok(rapVisites);
    }

    /**
     * Récupérer une rapport de visite par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RapportVisite> getRapportVisiteById(@PathVariable Long id) {
        Optional<RapportVisite> rapVisite = rapportVService.findById(id);
        if (rapVisite.isPresent()) {
            return ResponseEntity.ok(rapVisite.get());
        }
        
        else return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau rapport de visite.
     */
    @PostMapping
    public ResponseEntity<RapportVisite> createRapportVisite(@Valid @RequestBody RapportVisiteDto rapVisiteDto) {
    	Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));
		
		// Crée et insère le rapport
		RapportVisite newRapVisite = rapVisiteDto.toRapportVisite(etab);
		rapportVService.insert(newRapVisite);
				
		// Renvoie la réponse avec le rapport créé et l'ID généré
    	return ResponseEntity.ok(newRapVisite);
    }

  
}
