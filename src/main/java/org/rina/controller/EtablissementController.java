package org.rina.controller;

import org.rina.dao.IEtablissementJpaDao;
import org.rina.model.Etablissement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etablissements")
public class EtablissementController {

    @Autowired
    private IEtablissementJpaDao etablissementDao;

    @GetMapping
    public List<Etablissement> getAllEtablissements() {
        return etablissementDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etablissement> getEtablissementById(@PathVariable Long id) {
        Optional<Etablissement> etablissement = etablissementDao.findById(id);
        if (etablissement.isPresent()) {
            return ResponseEntity.ok(etablissement.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Etablissement createEtablissement(@RequestBody Etablissement etablissement) {
        return etablissementDao.save(etablissement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etablissement> updateEtablissement(@PathVariable Long id, @RequestBody Etablissement etablissementDetails) {
        Optional<Etablissement> existingEtablissement = etablissementDao.findById(id);

        if (existingEtablissement.isPresent()) {
            Etablissement updatedEtablissement = existingEtablissement.get();
            updatedEtablissement.setNom(etablissementDetails.getNom());
            updatedEtablissement.setEmail1(etablissementDetails.getEmail1());
            updatedEtablissement.setEmail2(etablissementDetails.getEmail2());
            updatedEtablissement.setTel1(etablissementDetails.getTel1());
            updatedEtablissement.setTel2(etablissementDetails.getTel2());
            updatedEtablissement.setAdresse(etablissementDetails.getAdresse());
            updatedEtablissement.setDateCreation(etablissementDetails.getDateCreation());
            return ResponseEntity.ok(etablissementDao.save(updatedEtablissement));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable Long id) {
        if (etablissementDao.existsById(id)) {
            etablissementDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}