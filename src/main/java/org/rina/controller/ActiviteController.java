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

		else return ResponseEntity.notFound().build();
	}

	@PostMapping("/{idEtab}")
	public Activite createActivity(@Valid @RequestBody ActiviteDto actDto, @RequestParam Long idEtab) {
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		return activiteService.insert(actDto.toActivite(etab));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Activite> updateActivity(@PathVariable Long id, @Valid @RequestBody ActiviteDto actDto) {
		// Vérifie d'abord si l'activité existe en fonction de l'ID
	    Optional<Activite> optionalActivite = activiteService.findById(id);
	    if (optionalActivite.isPresent()) {
	        Activite existingActivite = optionalActivite.get(); 
	        Etablissement etab = etablissementService.findById(existingActivite.getEtablissement().getId())
	                .orElseThrow(() -> new NotExistException(existingActivite.getEtablissement().getId().toString()));

			// Mise à jour l'activité existante avec les nouvelles valeurs
	        actDto.setId(id);
	        return ResponseEntity.ok(activiteService.updateActivite(id, actDto.toActivite(etab)));
	    }
	    
	    else return ResponseEntity.notFound().build();	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
		if (activiteService.existsById(id)) {
			activiteService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		 else return ResponseEntity.notFound().build();
	}
}