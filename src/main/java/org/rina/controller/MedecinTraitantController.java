package org.rina.controller;

import org.rina.dto.request.MedecinTraitantDto;
import org.rina.dto.response.MedecinTraitantResponseDto;
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

    /**
     * Récupérer un medecinTraitant par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedecinTraitantResponseDto> getmedecinTById(@PathVariable Long id) {
        // Chercher un médecin traitant par ID
        Optional<MedecinTraitant> medecinT = medecinService.findById(id);
        if (medecinT.isPresent()) {
            // Convertir le médecin traitant en DTO de réponse
            MedecinTraitantResponseDto medecinResponseDto = new MedecinTraitantResponseDto(medecinT.get());
            return ResponseEntity.ok(medecinResponseDto);
        } 
        else {
            // Renvoyer une réponse 404 si le médecin traitant n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Créer un nouveau medecinTraitant.
     */
    @PostMapping
    public ResponseEntity<MedecinTraitantResponseDto> createmedecinT(@Valid @RequestBody MedecinTraitantDto medecinDto) {
        // Créer un médecin traitant à partir des données fournies
        MedecinTraitant newMedecinT = medecinDto.toMedecinTraitant();
        // Insèrer le médecin traitant dans la base de données
        medecinService.insert(newMedecinT);

        // Créer un DTO de réponse avec les données du médecin traitant
        MedecinTraitantResponseDto medecinResponseDto = new MedecinTraitantResponseDto(newMedecinT);

        // Renvoyer la réponse avec le DTO de réponse
        return ResponseEntity.ok(medecinResponseDto);
    }

    /**
     * Mettre à jour un medecinTraitant existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedecinTraitantResponseDto> updatemedecinT(@PathVariable Long id, @Valid @RequestBody MedecinTraitantDto medecinDto) {
        // Vérifie d'abord si le médecin traitant existe en fonction de l'ID
        Optional<MedecinTraitant> existingMedecinT = medecinService.findById(id);

        if (existingMedecinT.isPresent()) {
            // Met à jour le médecin traitant existant avec les nouvelles valeurs
            medecinDto.setId(id);
            MedecinTraitant updateMedecinT = medecinService.updateMedecinTraitant(id, medecinDto.toMedecinTraitant());

            // Crée un DTO de réponse avec les données du médecin mis à jour
            MedecinTraitantResponseDto medecinResponseDto = new MedecinTraitantResponseDto(updateMedecinT);

            // Renvoie la réponse avec le DTO de réponse
            return ResponseEntity.ok(medecinResponseDto);
        } 
        else {
            // Renvoie une réponse 404 si le médecin traitant n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
}