package org.rina.controller;

import org.rina.dto.request.MedecinTraitantDto;

import org.rina.model.MedecinTraitant;
import org.rina.service.MedecinTraitantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class MedecinTraitantController {

    @Autowired
    private MedecinTraitantServices medecinService;

    @GetMapping("/{id}")
    public ResponseEntity<MedecinTraitant> getDoctorById(@PathVariable Long id) {
        Optional<MedecinTraitant> medecinT = medecinService.findById(id);
        if (medecinT.isPresent()) {
        	return ResponseEntity.ok(medecinT.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public MedecinTraitant createDoctor(@Valid @RequestBody MedecinTraitantDto medecinDto) {
        return medecinService.insert(medecinDto.toMedecinTraitant());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinTraitant> updateDoctor(@PathVariable Long id, @Valid @RequestBody MedecinTraitantDto medecinDto) {
        Optional<MedecinTraitant> existingMedecinTraitant = medecinService.findById(id);

        if (existingMedecinTraitant.isPresent()) {
            MedecinTraitant updatedMedecinTraitant = existingMedecinTraitant.get();
           updatedMedecinTraitant.setNom(medecinDto.getNom());
            updatedMedecinTraitant.setPrenom(medecinDto.getPrenom());
            updatedMedecinTraitant.setEmail(medecinDto.getEmail());
            updatedMedecinTraitant.setTel1(medecinDto.getTel1());
           updatedMedecinTraitant.setTel2(medecinDto.getTel2());
updatedMedecinTraitant.setAdresse(medecinDto.getAdresse());
            return ResponseEntity.ok(medecinService.update(updatedMedecinTraitant));
        }
        
        return ResponseEntity.notFound().build();
    }


}
