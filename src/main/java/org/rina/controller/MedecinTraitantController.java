package org.rina.controller;


import org.rina.dao.IMedecinTraitantJpaDao;
import org.rina.model.MedecinTraitant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medecins-traitants")
public class MedecinTraitantController {

    @Autowired
    private IMedecinTraitantJpaDao medecinTraitantDao;

    @GetMapping
    public List<MedecinTraitant> getAllMedecinsTraitants() {
        return medecinTraitantDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinTraitant> getMedecinTraitantById(@PathVariable Long id) {
        Optional<MedecinTraitant> medecinTraitant = medecinTraitantDao.findById(id);
        return medecinTraitant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedecinTraitant createMedecinTraitant(@RequestBody MedecinTraitant medecinTraitant) {
        return medecinTraitantDao.save(medecinTraitant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinTraitant> updateMedecinTraitant(@PathVariable Long id, @RequestBody MedecinTraitant medecinTraitantDetails) {
        Optional<MedecinTraitant> existingMedecinTraitant = medecinTraitantDao.findById(id);

        if (existingMedecinTraitant.isPresent()) {
            MedecinTraitant updatedMedecinTraitant = existingMedecinTraitant.get();
            updatedMedecinTraitant.setNom(medecinTraitantDetails.getNom());
            updatedMedecinTraitant.setPrenom(medecinTraitantDetails.getPrenom());
            updatedMedecinTraitant.setEmail(medecinTraitantDetails.getEmail());
            updatedMedecinTraitant.setTel1(medecinTraitantDetails.getTel1());
            updatedMedecinTraitant.setTel2(medecinTraitantDetails.getTel2());
            updatedMedecinTraitant.setAdresse(medecinTraitantDetails.getAdresse());
            return ResponseEntity.ok(medecinTraitantDao.save(updatedMedecinTraitant));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedecinTraitant(@PathVariable Long id) {
        if (medecinTraitantDao.existsById(id)) {
            medecinTraitantDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
