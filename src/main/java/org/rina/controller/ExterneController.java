package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.RapportVisiteDto;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.service.EtablissementServices;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externe")
public class ExterneController {

	@Autowired
	private RapportVisiteServices rapportVService;
	@Autowired
	private EtablissementServices etablissementService;
	
	/**
     * Créer un nouveau rapport de visite.
     */
    @PostMapping
    public ResponseEntity<String> createRapportVisite(@Valid @RequestBody RapportVisiteDto rapVisiteDto) {
        Long idEtab = Long.valueOf(1);
        // Récupérer l'établissement associé au rapport de visite
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Créer et insérer le rapport de visite dans la base de données
        RapportVisite newRapVisite = rapVisiteDto.toRapportVisite(etab);
        rapportVService.insert(newRapVisite);

        // Renvoyer une réponse 200 avec un message de confirmation
        return ResponseEntity.status(HttpStatus.OK).body("Merci pour votre Commentaire.");
    }

}