package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.response.ActiviteResponseDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

    @Autowired
    private ActiviteServices activiteService;
    @Autowired
    private EtablissementServices etablissementService;

    @GetMapping
    public ResponseEntity<List<ActiviteResponseDto>> getAllActivity() {
        // Récupère la liste de toutes les activités
        List<Activite> activities = activiteService.findAll();

        // Convertit la liste d'activités en liste d'ActiviteResponseDto
        List<ActiviteResponseDto> actDtoList = activities.stream()
                .map(ActiviteResponseDto::new)
                .collect(Collectors.toList());

        // Renvoie la liste des activités en réponse
        return ResponseEntity.ok(actDtoList);
    }

    /**
     * Récupérer une activité par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActiviteResponseDto> getActivityById(@PathVariable Long id) {
        // Recherche une activité par son ID
        Optional<Activite> existingActivite = activiteService.findById(id);

        // Si l'activité existe, renvoie-la en réponse sous forme d'ActiviteResponseDto
        if (existingActivite.isPresent()) {
            Activite activite = existingActivite.get();
            ActiviteResponseDto actDto = new ActiviteResponseDto(activite);
            return ResponseEntity.ok(actDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Créer une nouvelle activité.
     */
    @PostMapping
    public ResponseEntity<Activite> createActivity(@Valid @RequestBody ActiviteDto actDto) {
        // Obtient l'établissement associé à l'activité
        Long idEtab = Long.valueOf(1);
        Etablissement etab = etablissementService.findById(idEtab)
                .orElseThrow(() -> new NotExistException(idEtab.toString()));

        // Crée une nouvelle activité à partir des données de la requête
        Activite newActivite = actDto.toActivite(etab);
        // Insère la nouvelle activité dans la base de données
        activiteService.insert(newActivite);
        // Renvoie une réponse avec la nouvelle activité créée et l'ID généré
        return ResponseEntity.ok(newActivite);
    }

    /**
     * Mettre à jour une activité existante.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Activite> updateActivity(@PathVariable Long id, @Valid @RequestBody ActiviteDto actDto) {
        // Vérifie si l'activité existe en fonction de l'ID
        Optional<Activite> existingActivite = activiteService.findById(id);

        if (existingActivite.isPresent()) {
            // Obtient l'établissement associé à l'activité
            Long idEtab = Long.valueOf(1);
            Etablissement etab = etablissementService.findById(idEtab)
                    .orElseThrow(() -> new NotExistException(idEtab.toString()));

            // Met à jour l'activité existante avec les nouvelles valeurs
            actDto.setId(id);
            Activite updateActivite = activiteService.updateActivite(id, actDto.toActivite(etab));
            
            // Renvoie une réponse avec l'activité mise à jour
            return ResponseEntity.ok(updateActivite);
        } else {
            // Si l'activité n'existe pas, renvoie une réponse 404 (non trouvée)
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprimer une activité par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        // Vérifie si l'activité existe en fonction de l'ID
        if (activiteService.existsById(id)) {
            // Supprime l'activité si elle existe
            activiteService.deleteById(id);
            // Renvoie une réponse 200 (OK) pour indiquer que l'activité a été supprimée avec succès
            return ResponseEntity.ok().build();
        } else {
            // Si l'activité n'existe pas, renvoie une réponse 404 (non trouvée)
            return ResponseEntity.notFound().build();
        }
    }
}
