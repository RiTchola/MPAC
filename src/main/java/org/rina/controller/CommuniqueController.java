package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.CommuniqueDto;
import org.rina.model.Communique;
import org.rina.model.Etablissement;
import org.rina.service.CommuniqueServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/communique")
public class CommuniqueController {

	@Autowired
	private CommuniqueServices communiqueService;
	@Autowired
	private EtablissementServices etablissementService;

	@GetMapping("/")
	public List<Communique> getAllCommunique() {
		return communiqueService.findAllCommuniqueOrderByDateDesc();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Communique> getCommuniqueById(@PathVariable Long id) {
		Optional<Communique> communique = communiqueService.findById(id);
		if (communique.isPresent()) {
			return ResponseEntity.ok(communique.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{idEtab}")
	public Communique createCommunique(@Valid @RequestBody CommuniqueDto comDto, @RequestParam Long idEtab) {
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		return communiqueService.insert(comDto.toCommunique(etab));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Communique> updateCommunique(@PathVariable Long id, @Valid @RequestBody CommuniqueDto comDto) {
		Communique newCommunique = communiqueService.findById(comDto.getId())
				.orElseThrow(() -> new NotExistException(comDto.getId().toString()));
		Etablissement etab = etablissementService.findById(newCommunique.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(newCommunique.getEtablissement().getId().toString()));

		if (comDto.getId().equals(id)) {
//            Communique updatedCommunique = existingCommunique.get();
//            updatedCommunique.setContenu(communiqueDetails.getContenu());
//            updatedCommunique.setDate(communiqueDetails.getDate());
			return ResponseEntity.ok(communiqueService.update(comDto.toCommunique(etab)));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCommunique(@PathVariable Long id) {
		if (communiqueService.existsById(id)) {
			communiqueService.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}