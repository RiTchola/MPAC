package org.rina.controller;

import org.rina.dao.ICommuniqueJpaDao;
import org.rina.model.Communique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/communiques")
public class CommuniqueController {

    @Autowired
    private ICommuniqueJpaDao communiqueDao;

    @GetMapping
    public List<Communique> getAllCommuniques() {
        return communiqueDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Communique> getCommuniqueById(@PathVariable Long id) {
        Optional<Communique> communique = communiqueDao.findById(id);
        if (communique.isPresent()) {
            return ResponseEntity.ok(communique.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Communique createCommunique(@RequestBody Communique communique) {
        return communiqueDao.save(communique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Communique> updateCommunique(@PathVariable Long id, @RequestBody Communique communiqueDetails) {
        Optional<Communique> existingCommunique = communiqueDao.findById(id);

        if (existingCommunique.isPresent()) {
            Communique updatedCommunique = existingCommunique.get();
            updatedCommunique.setContenu(communiqueDetails.getContenu());
            updatedCommunique.setDate(communiqueDetails.getDate());
            return ResponseEntity.ok(communiqueDao.save(updatedCommunique));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunique(@PathVariable Long id) {
        if (communiqueDao.existsById(id)) {
            communiqueDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}