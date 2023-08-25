package org.rina.controller;


import org.rina.dao.IMeetUpJpaDao;
import org.rina.model.MeetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meetups")
public class MeetUpController {

    @Autowired
    private IMeetUpJpaDao meetUpDao;

    @GetMapping
    public List<MeetUp> getAllMeetUps() {
        return meetUpDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetUp> getMeetUpById(@PathVariable Long id) {
        Optional<MeetUp> meetUp = meetUpDao.findById(id);
        return meetUp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MeetUp createMeetUp(@RequestBody MeetUp meetUp) {
        return meetUpDao.save(meetUp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetUp> updateMeetUp(@PathVariable Long id, @RequestBody MeetUp meetUpDetails) {
        Optional<MeetUp> existingMeetUp = meetUpDao.findById(id);

        /*if (existingMeetUp.isPresent()) {
            MeetUp updatedMeetUp = existingMeetUp.get();
            updatedMeetUp.setTypeA(meetUpDetails.getTypeA());
            updatedMeetUp.setMotif(meetUpDetails.getMotif());
            updatedMeetUp.setDate(meetUpDetails.getDate());
            updatedMeetUp.setNomResident(meetUpDetails.getNomResident());
            updatedMeetUp.setPrenomResident(meetUpDetails.getPrenomResident());
            updatedMeetUp.setDateNaissanceR(meetUpDetails.getDateNaissanceR());
            updatedMeetUp.setNbrParticipants(meetUpDetails.getNbrParticipants());
            updatedMeetUp.setEtat(meetUpDetails.getEtat());
            return ResponseEntity.ok(meetUpDao.save(updatedMeetUp));
        }*/
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeetUp(@PathVariable Long id) {
        if (meetUpDao.existsById(id)) {
            meetUpDao.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}