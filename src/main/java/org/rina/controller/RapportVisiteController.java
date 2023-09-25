package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.RapportVisiteDto;
import org.rina.dto.response.RapportVisitResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.RapportVisite;
import org.rina.service.EtablissementServices;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<RapportVisitResponseDto>> getAllRapportVisite() {
        // Récupérer la liste de tous les rapports de visites triés par date décroissante
        List<RapportVisite> rapVisites = rapportVService.findAllRapportVisiteOrderByDateDesc();

        // Mapper les rapports de visite en DTOs
        List<RapportVisitResponseDto> rapViDtos = rapVisites.stream()
                .map(RapportVisitResponseDto::new)
                .collect(Collectors.toList());

        // Renvoyer la liste des rapports en réponse
        return ResponseEntity.ok(rapViDtos);
    }

    /**
     * Récupérer un rapport de visite par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RapportVisitResponseDto> getRapportVisiteById(@PathVariable Long id) {
        // Vérifier si un rapport de visite existe pour l'ID spécifié
        Optional<RapportVisite> rapVisite = rapportVService.findById(id);
        if (rapVisite.isPresent()) {
            // Mapper le rapport de visite en DTO
            RapportVisitResponseDto rapVisiteDto = new RapportVisitResponseDto(rapVisite.get());
            
            // Renvoyer le DTO de rapport de visite en réponse
            return ResponseEntity.ok(rapVisiteDto);
        } else {
            // Renvoyer une réponse 404 si le rapport de visite n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

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
        return ResponseEntity.status(HttpStatus.OK).body("Merci pour votre commentaire.");
    }

}