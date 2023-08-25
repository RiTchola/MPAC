package org.rina.controller;

import org.rina.model.RapportQuotidien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.rina.dao.IRapportQuotidienJpaDao;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rapports-quotidiens")
public class RapportQuotidienController {

    @Autowired
    private IRapportQuotidienJpaDao rapportQuotidienDao;

    @GetMapping
    public List<RapportQuotidien> getAllRapportsQuotidiens() {
        return rapportQuotidienDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportQuotidien> getRapportQuotidienById(@PathVariable Long id) {
        Optional<RapportQuotidien> rapportQuotidien = rapportQuotidienDao.findById(id);
        if (rapportQuotidien.isPresent()) {
            return ResponseEntity.ok(rapportQuotidien.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public RapportQuotidien createRapportQuotidien(@RequestBody RapportQuotidien rapportQuotidien) {
        return rapportQuotidienDao.save(rapportQuotidien);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RapportQuotidien> updateRapportQuotidien(@PathVariable Long id, @RequestBody RapportQuotidien rapportDetails) {
        Optional<RapportQuotidien> existingRapport = rapportQuotidienDao.findById(id);

        if (existingRapport.isPresent()) {
            RapportQuotidien updatedRapport = existingRapport.get();
            updatedRapport.setNumeroR(rapportDetails.getNumeroR());
            updatedRapport.setFreqCardiaque(rapportDetails.getFreqCardiaque());
            updatedRapport.setFreqRespiratoire(rapportDetails.getFreqRespiratoire());
            updatedRapport.setPresArterielle(rapportDetails.getPresArterielle());
            updatedRapport.setTemperature(rapportDetails.getTemperature());
            updatedRapport.setSatOxygene(rapportDetails.getSatOxygene());
            updatedRapport.setSelle(rapportDetails.getSelle());
            updatedRapport.setUrine(rapportDetails.getUrine());
            updatedRapport.setSommeil(rapportDetails.getSommeil());
            updatedRapport.setCommentaire(rapportDetails.getCommentaire());
            return ResponseEntity.ok(rapportQuotidienDao.save(updatedRapport));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapportQuotidien(@PathVariable Long id) {
        if (rapportQuotidienDao.existsById(id)) {
            rapportQuotidienDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}