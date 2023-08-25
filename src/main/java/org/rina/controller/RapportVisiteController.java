package org.rina.controller;


import org.rina.dao.IRapportVisiteJpaDao;
import org.rina.model.RapportVisite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rapports-visites")
public class RapportVisiteController {

    @Autowired
    private IRapportVisiteJpaDao rapportVisiteDao;

    @GetMapping
    public List<RapportVisite> getAllRapportsVisites() {
        return rapportVisiteDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportVisite> getRapportVisiteById(@PathVariable Long id) {
        Optional<RapportVisite> rapportVisite = rapportVisiteDao.findById(id);
        if (rapportVisite.isPresent()) {
            return ResponseEntity.ok(rapportVisite.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public RapportVisite createRapportVisite(@RequestBody RapportVisite rapportVisite) {
        return rapportVisiteDao.save(rapportVisite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RapportVisite> updateRapportVisite(@PathVariable Long id, @RequestBody RapportVisite rapportDetails) {
        Optional<RapportVisite> existingRapport = rapportVisiteDao.findById(id);

        if (existingRapport.isPresent()) {
            RapportVisite updatedRapport = existingRapport.get();
            updatedRapport.setDateVisite(rapportDetails.getDateVisite());
            updatedRapport.setCommentaire(rapportDetails.getCommentaire());
            return ResponseEntity.ok(rapportVisiteDao.save(updatedRapport));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapportVisite(@PathVariable Long id) {
        if (rapportVisiteDao.existsById(id)) {
            rapportVisiteDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
