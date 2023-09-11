package org.rina.controller;

import org.rina.dto.request.EtablissementDto;

import org.rina.model.Etablissement;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/establishment")
public class EtablissementController {

    @Autowired
    private EtablissementServices etablissementService;

    
//    @GetMapping("/")
//    public List<Etablissement> getAllEstablishment() {
//        return etablissementService.findAll();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Etablissement> getEstablishmentById(@PathVariable Long id) {
        Optional<Etablissement> etablissement = etablissementService.findById(id);
        if (etablissement.isPresent()) {
            return ResponseEntity.ok(etablissement.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Etablissement createEstablishment(@Valid @RequestBody EtablissementDto etabDto) {
        return etablissementService.insert(etabDto.toEtablissement());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etablissement> updateEstablishment(@PathVariable Long id, @Valid @RequestBody EtablissementDto etabDto) {
        //Optional<Etablissement> existingEtablissement = etablissementService.findById(id);

        if (etabDto.getId().equals(id)) {
//            Etablissement updatedEtablissement = existingEtablissement.get();
//            updatedEtablissement.setNom(etablissementDetails.getNom());
//            updatedEtablissement.setEmail1(etablissementDetails.getEmail1());
//            updatedEtablissement.setEmail2(etablissementDetails.getEmail2());
//            updatedEtablissement.setTel1(etablissementDetails.getTel1());
//            updatedEtablissement.setTel2(etablissementDetails.getTel2());
//            updatedEtablissement.setAdresse(etablissementDetails.getAdresse());
//            updatedEtablissement.setDateCreation(etablissementDetails.getDateCreation());
            return ResponseEntity.ok(etablissementService.update(etabDto.toEtablissement()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        if (etablissementService.existsById(id)) {
            etablissementService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}