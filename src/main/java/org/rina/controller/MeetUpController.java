package org.rina.controller;


import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MeetUpDto;
import org.rina.model.Etablissement;
import org.rina.model.MeetUp;
import org.rina.model.PersonneContact;
import org.rina.service.EtablissementServices;
import org.rina.service.MeetUpServices;
import org.rina.service.PersonneContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meetup")
public class MeetUpController {

    @Autowired
    private MeetUpServices meetUpService;
    @Autowired
    private EtablissementServices etablissementService;
    @Autowired
   	private PersonneContactServices persCService;


    @GetMapping
    public List<MeetUp> getAllMeetUps() {
        return meetUpService.findAllMeetOrderByDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetUp> getMeetUpById(@PathVariable Long id) {
        Optional<MeetUp> meetUp = meetUpService.findById(id);
        if (meetUp.isPresent()) {
			return ResponseEntity.ok(meetUp.get());
		}
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{idEtab}/{idPersC}")
    public MeetUp createMeetUp(@Valid @RequestBody MeetUpDto meetUpDto, @RequestParam Long idEtab, @RequestParam Long idPersC) {
    	Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));
    	PersonneContact persC = persCService.findById(idPersC)
				.orElseThrow(() -> new NotExistException(idPersC.toString()));
    	
    	return meetUpService.insert(meetUpDto.toMeetUp(etab, persC));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetUp> updateMeetUp(@PathVariable Long id, @Valid @RequestBody MeetUpDto meetUpDto) {
       MeetUp newMeetUp = meetUpService.findById(id)
    		   .orElseThrow(() -> new NotExistException(id.toString()));
       Etablissement etab = etablissementService.findById(newMeetUp.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(newMeetUp.getEtablissement().getId().toString()));
       PersonneContact persC = persCService.findById(newMeetUp.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(newMeetUp.getEtablissement().getId().toString())); 
  
       if (meetUpDto.getId().equals(id)) {

//            updatedMeetUp.setTypeA(meetUpDetails.getTypeA());
//            updatedMeetUp.setMotif(meetUpDetails.getMotif());
//            updatedMeetUp.setDate(meetUpDetails.getDate());
//            updatedMeetUp.setNomResident(meetUpDetails.getNomResident());
//            updatedMeetUp.setPrenomResident(meetUpDetails.getPrenomResident());
//            updatedMeetUp.setDateNaissanceR(meetUpDetails.getDateNaissanceR());
//            updatedMeetUp.setNbrParticipants(meetUpDetails.getNbrParticipants());
//            updatedMeetUp.setEtat(meetUpDetails.getEtat());
            return ResponseEntity.ok(meetUpService.update(meetUpDto.toMeetUp(etab, persC)));
        }
       
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeetUp(@PathVariable Long id) {
        if (meetUpService.existsById(id)) {
            meetUpService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}