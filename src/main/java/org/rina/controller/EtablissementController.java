package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EtablissementDto;

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
     * Récupérer un etablissement.
     */
    @GetMapping
    public ResponseEntity<Etablissement> getEstablishment() {
    	Long idEtab = Long.valueOf(1);
        Optional<Etablissement> etablissement = etablissementService.findById(idEtab);
        if (etablissement.isPresent()) {
            return ResponseEntity.ok(etablissement.get());
        }
        
        else return ResponseEntity.notFound().build();
    }

    /**
     * Créer un nouvel etablissement.
     */
    @PostMapping
    public ResponseEntity<String> createEstablishment(@Valid @RequestBody EtablissementDto etabDto) {
        // Vérifie qu'il n'existe pas d'établissement
        if (etablissementService.count() == 0) {
        	
            User user = userService.findById(etabDto.getIdEtab())
            		.orElseThrow(() -> new NotExistException(etabDto.getIdEtab().toString()));
            
          //Crée et insère l'établissement
			Etablissement newEtab = etabDto.toEtablissement(user);
			etablissementService.insert(newEtab);
			
			return ResponseEntity.ok().build();
        } 
        
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("L'établissement existe déjà.");
    }
    
    /**
     * Mettre à jour un etablissement existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Etablissement> updateEstablishment(@PathVariable Long id, @Valid @RequestBody EtablissementDto etabDto) {
    	// Vérifie d'abord si l'établissement existe en fonction de l'ID
    	Optional<Etablissement> existingEtablissement = etablissementService.findById(id);

        if (existingEtablissement.isPresent()) {
        	
        	// Mise à jour l'établissement existant avec les nouvelles valeurs
        	User user = userService.findById(etabDto.getIdEtab())
            		.orElseThrow(() -> new NotExistException(etabDto.getIdEtab().toString()));
        	etabDto.setId(id);
        	Etablissement updateEtab = etablissementService.updateEtablissement(id, etabDto.toEtablissement(user));
            
        	//Renvoie la réponse avec l'établissement mis à jour
        	return ResponseEntity.ok(updateEtab);
        }
        
        else return ResponseEntity.notFound().build();
    }

}