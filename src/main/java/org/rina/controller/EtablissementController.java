package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EtablissementDto;
import org.rina.dto.response.EtablissementResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.User;
import org.rina.service.EtablissementServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/establishment")
public class EtablissementController {

    @Autowired
    private EtablissementServices etablissementService;

    @Autowired
    private UserService userService;

    /**
     * Récupère un établissement par ID.
     */
    @GetMapping
    public ResponseEntity<EtablissementResponseDto> getEstablishment() {
        // Récupère un établissement par ID (dans cet exemple, ID 1 est utilisé comme exemple)
        Long idEtab = Long.valueOf(1);
        Optional<Etablissement> etablissement = etablissementService.findById(idEtab);
        if (etablissement.isPresent()) {
            // Convertit l'établissement en DTO et renvoie une réponse OK
            EtablissementResponseDto etabResponseDto = new EtablissementResponseDto(etablissement.get());
            return ResponseEntity.ok(etabResponseDto);
        } else {
            // Renvoie une réponse "non trouvé" si l'établissement n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crée un nouvel établissement.
     */
    @PostMapping
    public ResponseEntity<String> createEstablishment(@Valid @RequestBody EtablissementDto etabDto) {
        // Vérifie s'il n'existe pas déjà d'établissement
        if (etablissementService.count() == 0) {
            User user = userService.findByUsername(etabDto.getEtabUsername())
                    .orElseThrow(() -> new NotExistException(etabDto.getEtabUsername().toString()));

            // Crée et insère le nouvel établissement
            Etablissement newEtab = etabDto.toEtablissement(user);
            etablissementService.insert(newEtab);

            return ResponseEntity.ok().build();
        } else {
            // Renvoie une réponse "interdit" si un établissement existe déjà
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("L'établissement existe déjà.");
        }
    }

    /**
     * Met à jour un établissement existant par ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtablissementResponseDto> updateEstablishment(@PathVariable Long id, @Valid @RequestBody EtablissementDto etabDto) {
        // Vérifie d'abord si l'établissement existe en fonction de l'ID
        Optional<Etablissement> existingEtablissement = etablissementService.findById(id);

        if (existingEtablissement.isPresent()) {
            User user = userService.findByUsername(etabDto.getEtabUsername())
                    .orElseThrow(() -> new NotExistException(etabDto.getEtabUsername().toString()));

            // Met à jour l'établissement existant avec les nouvelles valeurs
            etabDto.setId(id);
            Etablissement updateEtab = etablissementService.updateEtablissement(id, etabDto.toEtablissement(user));

            // Renvoie la réponse avec l'établissement mis à jour sous forme de DTO
            EtablissementResponseDto etabResponseDto = new EtablissementResponseDto(updateEtab);
            return ResponseEntity.ok(etabResponseDto);
        } else {
            // Renvoie une réponse "non trouvé" si l'établissement n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
}
