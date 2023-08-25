package org.rina.controller;


import org.rina.dao.IPersonneContactJpaDao;
import org.rina.model.PersonneContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personnes-contact")
public class PersonneContactController {

    @Autowired
    private IPersonneContactJpaDao personneContactDao;

    @GetMapping
    public List<PersonneContact> getAllPersonneContacts() {
        return personneContactDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneContact> getPersonneContactById(@PathVariable Long id) {
        Optional<PersonneContact> personneContact = personneContactDao.findById(id);
        return personneContact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonneContact createPersonneContact(@RequestBody PersonneContact personneContact) {
        return personneContactDao.save(personneContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneContact> updatePersonneContact(@PathVariable Long id, @RequestBody PersonneContact personneContactDetails) {
        Optional<PersonneContact> existingPersonneContact = personneContactDao.findById(id);

        if (existingPersonneContact.isPresent()) {
            PersonneContact updatedPersonneContact = existingPersonneContact.get();
            updatedPersonneContact.setNom(personneContactDetails.getNom());
            updatedPersonneContact.setPrenom(personneContactDetails.getPrenom());
            updatedPersonneContact.setEmail(personneContactDetails.getEmail());
            updatedPersonneContact.setDateNaissance(personneContactDetails.getDateNaissance());
            updatedPersonneContact.setTel1(personneContactDetails.getTel1());
            updatedPersonneContact.setTel2(personneContactDetails.getTel2());
            updatedPersonneContact.setAdresse(personneContactDetails.getAdresse());
            updatedPersonneContact.setChoix(personneContactDetails.getChoix());
            updatedPersonneContact.setStatut(personneContactDetails.getStatut());
            return ResponseEntity.ok(personneContactDao.save(updatedPersonneContact));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonneContact(@PathVariable Long id) {
        if (personneContactDao.existsById(id)) {
            personneContactDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}