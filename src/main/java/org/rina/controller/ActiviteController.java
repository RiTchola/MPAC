package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.ActiviteDto;
import org.rina.model.Activite;
import org.rina.model.Etablissement;
import org.rina.service.ActiviteServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity")
public class ActiviteController {

	@Autowired
	private ActiviteServices activiteService;
	@Autowired
	private EtablissementServices etablissementService;

	/** 
	 * @return Liste des activités
	 */
	@GetMapping("/")
	public List<Activite> getAllActivity() {
		return activiteService.findAll();
	}

	/** 
	 * @param id
	 * @return Affiche le détail d'une activité
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Activite> getActivityById(@PathVariable Long id) {
		Optional<Activite> activite = activiteService.findById(id);
		if (activite.isPresent()) {
			return ResponseEntity.ok(activite.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{idEtab}")
	public Activite createActivity(@Valid @RequestBody ActiviteDto actDto, @RequestParam Long idEtab) {
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		return activiteService.insert(actDto.toActivite(etab));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Activite> updateActivity(@PathVariable Long id, @Valid @RequestBody ActiviteDto actDto) {
		Activite newActivite = activiteService.findById(actDto.getId())
				.orElseThrow(() -> new NotExistException(actDto.getId().toString()));
		Etablissement etab = etablissementService.findById(newActivite.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(newActivite.getEtablissement().getId().toString()));

		if (actDto.getId().equals(id)) {

//			Activite updatedActivite = newActivite.get();
//			updatedActivite.setNom(activiteDetails.getNom());
//			updatedActivite.setDate(activiteDetails.getDate());
//			updatedActivite.setDescription(activiteDetails.getDescription());
//			return ResponseEntity.ok(activiteService.insert(updatedActivite));
			return ResponseEntity.ok(activiteService.update(actDto.toActivite(etab)));
		}
		
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
		if (activiteService.existsById(id)) {
			activiteService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}