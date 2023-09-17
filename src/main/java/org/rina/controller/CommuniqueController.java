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

	
	/**
     * Récupérer tous les communiques.
     */
	@GetMapping
	public ResponseEntity<List<Communique>> getAllCommunique() {
		List<Communique> communiques = communiqueService.findAllCommuniqueOrderByDateDesc();
		return ResponseEntity.ok(communiques);
	}

	/**
     * Récupérer un communique par son ID.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Communique> getCommuniqueById(@PathVariable Long id) {
		Optional<Communique> communique = communiqueService.findById(id);
		if (communique.isPresent()) {
			return ResponseEntity.ok(communique.get());
		}

		else return ResponseEntity.notFound().build();
	}

	/**
     * Créer un nouveau communique.
     */
	@PostMapping
	public ResponseEntity<Communique> createCommunique(@Valid @RequestBody CommuniqueDto comDto) {
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		//Crée et insère le communique 
		Communique newCom = comDto.toCommunique(etab);
		communiqueService.insert(newCom);
		
		//Renvoie la réponse avec le communique créé et l'ID généré
		return ResponseEntity.ok(newCom);
	}

	/**
     * Mettre à jour un communique existant.
     */
	@PutMapping("/{id}")
	public ResponseEntity<Communique> updateCommunique(@PathVariable Long id, @Valid @RequestBody CommuniqueDto comDto) {
		//Vérifie d'abord si le communique existe en fonction de l'ID
		Optional<Communique> existingCommunique = communiqueService.findById(comDto.getId());

		if (existingCommunique.isPresent()) {
	    	Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			//Mise à jour du communique existant avec les nouvelles valeurs
	        comDto.setId(id);
	        Communique updateCom = communiqueService.updateCommunique(id, comDto.toCommunique(etab));
	        
	        //Renvoie la réponse avec le communique mis à jour 
	        return ResponseEntity.ok(updateCom);
	    }
	    
	    else return ResponseEntity.notFound().build();
	}

	/**
     * Supprimer un communique par son ID.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCommunique(@PathVariable Long id) {
		if (communiqueService.existsById(id)) {
			communiqueService.deleteById(id);
			return ResponseEntity.ok().build();
		}

		else return ResponseEntity.notFound().build();
	}
	
}