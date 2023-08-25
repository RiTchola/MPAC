package org.rina.controller;

import org.rina.dao.IEvenementJpaDao;
import org.rina.model.Evenement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private IEvenementJpaDao evenementDao;

    @GetMapping
    public List<Evenement> getAllEvenements() {
        return evenementDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEvenementById(@PathVariable Long id) {
        Optional<Evenement> evenement = evenementDao.findById(id);
        if (evenement.isPresent()) {
            return ResponseEntity.ok(evenement.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Evenement createEvenement(@RequestBody Evenement evenement) {
        return evenementDao.save(evenement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evenement> updateEvenement(@PathVariable Long id, @RequestBody Evenement evenementDetails) {
        Optional<Evenement> existingEvenement = evenementDao.findById(id);

        if (existingEvenement.isPresent()) {
            Evenement updatedEvenement = existingEvenement.get();
            updatedEvenement.setDateEvent(evenementDetails.getDateEvent());
            updatedEvenement.setDescription(evenementDetails.getDescription());
            return ResponseEntity.ok(evenementDao.save(updatedEvenement));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        if (evenementDao.existsById(id)) {
            evenementDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}