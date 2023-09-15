package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EvenementDto;
import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.rina.service.EtablissementServices;
import org.rina.service.EvenementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EvenementController {

	@Autowired
	private EvenementServices evenementService;
	@Autowired
	private EtablissementServices etablissementService;

	@GetMapping("/")
	public List<Evenement> getAllEvents() {
		return evenementService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Evenement> getEventById(@PathVariable Long id) {
		Optional<Evenement> evenement = evenementService.findById(id);
		if (evenement.isPresent()) {
			return ResponseEntity.ok(evenement.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{idEtab}")
	public Evenement createEvent(@Valid @RequestBody EvenementDto evenementDto) {
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		return evenementService.insert(evenementDto.toEvenement(etab));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Evenement> updateEvent(@PathVariable Long id, @Valid @RequestBody EvenementDto evenementDto) {
		Evenement newEvenement = evenementService.findById(evenementDto.getId())
				.orElseThrow(() -> new NotExistException(evenementDto.getId().toString()));
		Etablissement etab = etablissementService.findById(newEvenement.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(newEvenement.getEtablissement().getId().toString()));

		if (evenementDto.getId().equals(id)) {
//            Evenement updatedEvenement = existingEvenement.get();
//            updatedEvenement.setDateEvent(evenementDetails.getDateEvent());
			return ResponseEntity.ok(evenementService.update(evenementDto.toEvenement(etab)));
		}
		
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		if (evenementService.existsById(id)) {
			evenementService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}