package org.rina.controller;

import org.rina.dao.IActiviteJpaDao;
import org.rina.model.Activite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activites")
public class ActiviteController {

    @Autowired
    private IActiviteJpaDao activiteDao;

    @GetMapping
    public List<Activite> getAllActivites() {
        return activiteDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activite> getActiviteById(@PathVariable Long id) {
        Optional<Activite> activite = activiteDao.findById(id);
        return activite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Activite createActivite(@RequestBody Activite activite) {
        return activiteDao.save(activite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activite> updateActivite(@PathVariable Long id, @RequestBody Activite activiteDetails) {
        Optional<Activite> existingActivite = activiteDao.findById(id);

        if (existingActivite.isPresent()) {
            Activite updatedActivite = existingActivite.get();
            updatedActivite.setNom(activiteDetails.getNom());
            updatedActivite.setDate(activiteDetails.getDate());
            updatedActivite.setDescription(activiteDetails.getDescription());
            return ResponseEntity.ok(activiteDao.save(updatedActivite));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
        if (activiteDao.existsById(id)) {
            activiteDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}