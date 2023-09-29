package org.rina.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.dto.response.RapportVisitResponseDto;
import org.rina.model.RapportVisite;
import org.rina.service.RapportVisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rapport-visite")
public class RapportVisiteController {

    @Autowired
    private RapportVisiteServices rapportVService;

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
        } 
        else {
            // Renvoyer une réponse 200 si le rapport de visite n'existe pas
            return ResponseEntity.ok().build();
        }
    }

}