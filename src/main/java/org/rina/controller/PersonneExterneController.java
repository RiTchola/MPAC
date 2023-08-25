package org.rina.controller;


import org.rina.dao.IPersonneExterneJpaDao;
import org.rina.model.PersonneExterne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personnes-externes")
public class PersonneExterneController {

    @Autowired
    private IPersonneExterneJpaDao personneExterneDao;

    @GetMapping
    public List<PersonneExterne> getAllPersonneExternes() {
        return personneExterneDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneExterne> getPersonneExterneById(@PathVariable Long id) {
        Optional<PersonneExterne> personneExterne = personneExterneDao.findById(id);
        return personneExterne.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonneExterne createPersonneExterne(@RequestBody PersonneExterne personneExterne) {
        return personneExterneDao.save(personneExterne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneExterne> updatePersonneExterne(@PathVariable Long id, @RequestBody PersonneExterne personneExterneDetails) {
        Optional<PersonneExterne> existingPersonneExterne = personneExterneDao.findById(id);

        if (existingPersonneExterne.isPresent()) {
            PersonneExterne updatedPersonneExterne = existingPersonneExterne.get();
            updatedPersonneExterne.setNom(personneExterneDetails.getNom());
            updatedPersonneExterne.setPrenom(personneExterneDetails.getPrenom());
            updatedPersonneExterne.setEmail(personneExterneDetails.getEmail());
            updatedPersonneExterne.setTel(personneExterneDetails.getTel());
            updatedPersonneExterne.setChoix(personneExterneDetails.getChoix());
            return ResponseEntity.ok(personneExterneDao.save(updatedPersonneExterne));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonneExterne(@PathVariable Long id) {
        if (personneExterneDao.existsById(id)) {
            personneExterneDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}