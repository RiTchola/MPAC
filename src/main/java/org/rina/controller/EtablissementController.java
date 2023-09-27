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
        // Récupèrer un établissement par ID (dans cet application, ID 1 est toujours seul et unique)
        Long idEtab = Long.valueOf(1);
        Optional<Etablissement> etablissement = etablissementService.findById(idEtab);
        if (etablissement.isPresent()) {
            
        	// Convertir l'établissement en DTO et renvoie une réponse OK
            EtablissementResponseDto etabResponseDto = new EtablissementResponseDto(etablissement.get());
            return ResponseEntity.ok(etabResponseDto);
        } 
        else {
            // // Renvoyer une réponse 404 si l'établissement n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crée un nouvel établissement.
     */
    @PostMapping
    public ResponseEntity<String> createEstablishment(@Valid @RequestBody EtablissementDto etabDto) {
        // Vérifier s'il n'existe pas déjà d'établissement
        if (etablissementService.count() == 0) {
            User user = userService.findByUsername(etabDto.getEtabUsername())
                    .orElseThrow(() -> new NotExistException(etabDto.getEtabUsername().toString()));

            // Créer et insèrer le nouvel établissement
            Etablissement newEtab = etabDto.toEtablissement(user);
            etablissementService.insert(newEtab);

            // Renvoyer une réponse HTTP indiquant le succès de l'opération
            return ResponseEntity.status(HttpStatus.OK).body("Établissement crée");
        } 
        else {
        	// Renvoyer une réponse 404  si un établissement existe déjà
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("L'établissement existe déjà.");
        }
    }

    /**
     * Met à jour un établissement existant par ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtablissementResponseDto> updateEstablishment(@PathVariable Long id, @Valid @RequestBody EtablissementDto etabDto) {
        // Vérifier d'abord si l'établissement existe en fonction de l'ID
        Optional<Etablissement> existingEtablissement = etablissementService.findById(id);

        if (existingEtablissement.isPresent()) {
            User user = userService.findByUsername(etabDto.getEtabUsername())
                    .orElseThrow(() -> new NotExistException(etabDto.getEtabUsername().toString()));

            // Mettre à jour l'établissement existant avec les nouvelles valeurs
            etabDto.setId(id);
            Etablissement updateEtab = etablissementService.updateEtablissement(id, etabDto.toEtablissement(user));

            // Renvoyer la réponse avec l'établissement mis à jour sous forme de DTO
            EtablissementResponseDto etabResponseDto = new EtablissementResponseDto(updateEtab);
            return ResponseEntity.ok(etabResponseDto);
        } 
        else {
        	// Renvoyer une réponse 404 si l'établissement n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
}
