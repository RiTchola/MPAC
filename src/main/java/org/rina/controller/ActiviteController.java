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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

	private ActiviteServices activiteService;
	private EtablissementServices etablissementServices;

	// injection de l'accès au service
	@Autowired
	public ActiviteController(ActiviteServices activiteService, EtablissementServices etablissementServices) {
		this.activiteService = activiteService;
		this.etablissementServices = etablissementServices;
	}
	
	
	/**
	 * Liste des activite
	 * 
	 * @param model
	 * @return la nom logique de la vue qui affichera la liste des activite
	 */
	@GetMapping("/liste") public List<Activite> getAllActivites() {
		return activiteService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Activite> getActiviteById(@PathVariable Long id) {
		Optional<Activite> activite = activiteService.findById(id);
		return activite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{idEtab}")
	public Activite createActivite(@RequestBody ActiviteDto actDto, @RequestParam Long idEtab) {
		Etablissement etab = etablissementServices.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		return activiteService.insert(actDto.toActivite(etab));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Activite> updateActivite(@PathVariable Long id, @RequestBody ActiviteDto actDto) {
		Activite oldActivite = activiteService.findById(actDto.getId())
				.orElseThrow(() -> new NotExistException(actDto.getId().toString()));
		Etablissement etab = etablissementServices.findById(oldActivite.getEtablissement().getId())
				.orElseThrow(() -> new NotExistException(oldActivite.getEtablissement().getId().toString()));
		if (actDto.getId().equals(id))
			return ResponseEntity.ok(activiteService.update(actDto.toActivite(etab)));
//		Activite updatedActivite = oldActivite.get();
//		updatedActivite.setNom(activiteDetails.getNom());
//		updatedActivite.setDate(activiteDetails.getDate());
//		updatedActivite.setDescription(activiteDetails.getDescription());
//		return ResponseEntity.ok(activiteService.insert(updatedActivite));

		return ResponseEntity.ok().build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
		if (activiteService.existsById(id)) {
			activiteService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}