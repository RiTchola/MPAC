package org.rina.controller;


import org.rina.dao.IResidentJpaDao;
import org.rina.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    private IResidentJpaDao residentDao;

    @GetMapping
    public List<Resident> getAllResidents() {
        return residentDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Optional<Resident> resident = residentDao.findById(id);
        return resident.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Resident createResident(@RequestBody Resident resident) {
        return residentDao.save(resident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable Long id, @RequestBody Resident residentDetails) {
        Optional<Resident> existingResident = residentDao.findById(id);

        if (existingResident.isPresent()) {
            Resident residentToUpdate = existingResident.get();

            residentToUpdate.setNom(residentDetails.getNom());
            residentToUpdate.setPrenom(residentDetails.getPrenom());
            residentToUpdate.setDateNaissance(residentDetails.getDateNaissance());
            residentToUpdate.setEmail(residentDetails.getEmail());
            residentToUpdate.setTel(residentDetails.getTel());
            residentToUpdate.setAdresse(residentDetails.getAdresse());
            residentToUpdate.setStatut(residentDetails.getStatut());
            residentToUpdate.setDateEntree(residentDetails.getDateEntree());
            residentToUpdate.setMotifEntree(residentDetails.getMotifEntree());
            residentToUpdate.setDateSortie(residentDetails.getDateSortie());
            residentToUpdate.setMotifSortie(residentDetails.getMotifSortie());
            residentToUpdate.setAntMedical(residentDetails.getAntMedical());
            residentToUpdate.setAntChirugical(residentDetails.getAntChirugical());
            residentToUpdate.setNbEnfant(residentDetails.getNbEnfant());
            residentToUpdate.setChambre(residentDetails.getChambre());
            residentToUpdate.setEtatSante(residentDetails.getEtatSante());
            residentToUpdate.setActif(residentDetails.getActif());

            Resident updatedResident = residentDao.save(residentToUpdate);
            return ResponseEntity.ok(residentDao.save(updatedResident));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        if (residentDao.existsById(id)) {
            residentDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}