package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EvenementDto;
import org.rina.dto.response.EvenementResponseDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evenement")
public class EvenementController {

    @Autowired
    private EvenementServices evenementService;
    
    @Autowired
    private EtablissementServices etablissementService;

    /**
     * Récupérer tous les événements.
     */
    @GetMapping
    public ResponseEntity<List<EvenementResponseDto>> getAllEvents() {
        // Récupère la liste de tous les événements
        List<Evenement> evenements = evenementService.findAll();
        
        // Convertit la liste d'événements en une liste de DTO de réponse
        List<EvenementResponseDto> eventResponseDtoList = evenements.stream()
                .map(EvenementResponseDto::new)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(eventResponseDtoList);
    }

    /**
     * Récupérer un événement par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EvenementResponseDto> getEventById(@PathVariable Long id) {
        // Recherche un événement par son ID
        Optional<Evenement> existingEvenement = evenementService.findById(id);
        
        if (existingEvenement.isPresent()) {
            // Si l'événement existe, crée un DTO de réponse pour l'événement
            Evenement event = existingEvenement.get();
            EvenementResponseDto eventResponseDto = new EvenementResponseDto(event);
            return ResponseEntity.ok(eventResponseDto);
        }
        
        // Si l'événement n'est pas trouvé, renvoie une réponse "not found"
        return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouveau événement.
     */
    @PostMapping
    public ResponseEntity<EvenementResponseDto> createEvent(@Valid @RequestBody EvenementDto evenementDto) {
        // Obtient l'ID de l'établissement associé à l'événement (dans cet exemple, l'ID est 1)
        Long idEtab = Long.valueOf(1);
        
        // Recherche l'établissement par son ID
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Crée un nouvel événement à partir des données du DTO
        Evenement newEvenement = evenementDto.toEvenement(etab);
        
        // Insère le nouvel événement dans la base de données
        evenementService.insert(newEvenement);

        // Crée un DTO de réponse pour le nouvel événement
        EvenementResponseDto eventResponseDto = new EvenementResponseDto(newEvenement);
        return ResponseEntity.ok(eventResponseDto);
    }

    /**
     * Mettre à jour un événement existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EvenementResponseDto> updateEvent(@PathVariable Long id, @Valid @RequestBody EvenementDto evenementDto) {
        // Recherche l'événement existant par son ID
        Optional<Evenement> existingEvenement = evenementService.findById(id);

        if (existingEvenement.isPresent()) {
            // Obtient l'ID de l'établissement associé à l'événement (dans cet exemple, l'ID est 1)
            Long idEtab = Long.valueOf(1);
            
            // Recherche l'établissement par son ID
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));

            // Met à jour l'ID du DTO avec l'ID de l'événement
            evenementDto.setId(id);
            
            // Met à jour l'événement avec les données du DTO
            Evenement updatedEvenement = evenementService.updateEvenement(id, evenementDto.toEvenement(etab));

            // Crée un DTO de réponse pour l'événement mis à jour
            EvenementResponseDto eventResponseDto = new EvenementResponseDto(updatedEvenement);
            return ResponseEntity.ok(eventResponseDto);
        } else {
            // Si l'événement n'est pas trouvé, renvoie une réponse "not found"
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprimer un événement par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (evenementService.existsById(id)) {
            // Si l'événement existe, le supprime
            evenementService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        // Si l'événement n'est pas trouvé, renvoie une réponse "not found"
        return ResponseEntity.notFound().build();
    }
}