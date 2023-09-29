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
import org.rina.dto.response.RapportQuotidienResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rapport-quotidien")
public class RapportQuotidienController {

	@Autowired
	private RapportQuotidienServices rapportQuotService;
	@Autowired
	private ResidentServices residentService;
	
	// Format d'horodatage
	private static final String DATE_FORMAT = "yyyyMMddHHmmss"; 
	// Compteur auto-incrémenté
	private static final AtomicLong counter = new AtomicLong(0); 

	 /**
     * Récupérer tous les rapports quotidiens.
     */
    @GetMapping
    public ResponseEntity<List<RapportQuotidienResponseDto>> getAllRapportsQuotidiens() {
        // Récupérer la liste de tous les rapports quotidiens triés par date décroissante
        List<RapportQuotidien> rapquotidiens = rapportQuotService.findAllRapportQuotidienOrderByDateDesc();

        // Mapper les rapports quotidiens en DTOs
        List<RapportQuotidienResponseDto> rapQuotResponseDtos = rapquotidiens.stream()
                .map(RapportQuotidienResponseDto::new).collect(Collectors.toList());

        // Renvoyer la liste des rapports en réponse
        return ResponseEntity.ok(rapQuotResponseDtos);
    }

    /**
     * Récupérer un rapport quotidien par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RapportQuotidienResponseDto> getRapportQuotidienById(@PathVariable Long id) {
        // Vérifier si un rapport quotidien existe pour l'ID spécifié
        Optional<RapportQuotidien> rapportQuotidien = rapportQuotService.findById(id);
        if (rapportQuotidien.isPresent()) {
            // Mapper le rapport quotidien en un DTO et le renvoyer en réponse
            RapportQuotidienResponseDto rapQuotResponseDto = new RapportQuotidienResponseDto(rapportQuotidien.get());
            return ResponseEntity.ok(rapQuotResponseDto);
        } 
        else {
            // Renvoyer une réponse 200 si le rapport quotidien n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Créer un nouveau rapport quotidien.
     */
    @PostMapping("/{idResid}")
    public ResponseEntity<RapportQuotidienResponseDto> createRapportQuotidien(@PathVariable Long idResid, @Valid @RequestBody RapportQuotidienDto rapportQuotDto) {
        // Récupérer le résident lié
        Optional<Resident> existingResid = residentService.findById(idResid);

        if (existingResid.isPresent()) {
            Resident resid = existingResid.get();
            // Créer et insérer le rapport quotidien dans la base de données
            rapportQuotDto.setNumeroR(generateNumeroR());
            RapportQuotidien newRapportQ = rapportQuotService.insert(rapportQuotDto.toRapportQuotidien(resid));

            // Mapper le rapport créé en un DTO et le renvoyer en réponse avec l'ID généré
            RapportQuotidienResponseDto rapQuotResponseDto = new RapportQuotidienResponseDto(newRapportQ);
            return ResponseEntity.ok(rapQuotResponseDto);
        } 
        else {
            // Renvoyer une réponse 200 si le résident n'existe pas
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Mettre à jour un rapport quotidien existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RapportQuotidienResponseDto> updateRapportQuotidien(@PathVariable Long id,
            @Valid @RequestBody RapportQuotidienDto rapportQuotDto) {
        // Vérifie d'abord si le rapport quotidien existe en fonction de l'ID
        Optional<RapportQuotidien> existingRapport = rapportQuotService.findById(id);

        if (existingRapport.isPresent()) {
            RapportQuotidien rapQuot = existingRapport.get();
            Resident resid = rapQuot.getResident();
            String numero = rapportQuotDto.getNumeroR();
            
            // Mise à jour du rapport quotidien existant avec les nouvelles valeurs
            rapportQuotDto.setId(id);
            rapportQuotDto.setNumeroR(numero);
            RapportQuotidien updateRapportQuot = rapportQuotService.updateRapportQuotidien(id,
                    rapportQuotDto.toRapportQuotidien(resid));

            // Mapper le rapport mis à jour en un DTO et le renvoyer en réponse
            RapportQuotidienResponseDto rapQuotResponseDto = new RapportQuotidienResponseDto(updateRapportQuot);
            return ResponseEntity.ok(rapQuotResponseDto);
        } 
        else {
            // Renvoyer une réponse 200 si le rapport quotidien n'existe pas
            return ResponseEntity.ok().build();
        }
    }
    
    public static String generateNumeroR() {
        // Obtenir l'horodatage actuel
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(now);

        // Obtenir la valeur du compteur auto-incrémenté
        long count = counter.incrementAndGet();
        // Concaténer l'horodatage et la valeur du compteur pour créer le code unique
        String uniqueCode = timestamp + "-" + count;

        return uniqueCode;
    }
}