package org.rina.controller;

import java.util.List;

import java.util.Optional;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.FichierDto;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.rina.service.FichierServices;
import org.rina.service.PersonneContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/file")
public class FichierController{
	
	@Autowired
	private FichierServices fichierService;
	@Autowired
	private PersonneContactServices persCService;

	/**
	 * @return Liste des fichiers de l'établissement
	 */
	@GetMapping("/")
	public List<Fichier> getAllFile() {
		return fichierService.findAllFichierOrderByDateDesc();
	}
	
	/** 
	 * @return Liste des fichiers d'une personne de contact
	 */
	@GetMapping("/{idPersC}")
	public List<Fichier> getAllFileByPersonneContact(@RequestParam Long idPersC) {
		return fichierService.findAllByPersonneContactOrderByDateDesc(idPersC);
	}

	/** 
	 * @param id
	 * @return Affiche le détail d'un fichier
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Fichier> getFileById(@PathVariable Long id) {
		Optional<Fichier> fichier = fichierService.findById(id);
		if (fichier.isPresent()) {
			return ResponseEntity.ok(fichier.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{idPersC}")
	public Fichier createFile(@Valid @RequestBody FichierDto fichierDto, @RequestParam Long idPersC) {
		PersonneContact persC = persCService.findById(idPersC)
				.orElseThrow(() -> new NotExistException(idPersC.toString()));

		return fichierService.insert(fichierDto.toFichier(persC));
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Fichier> updateFile(@PathVariable Long id, @Valid @RequestBody FichierDto fichierDto) {
//		Fichier newFichier = fichierService.findById(fichierDto.getId())
//				.orElseThrow(() -> new NotExistException(fichierDto.getId().toString()));
//		PersonneContact persC = persCService.findById(newFichier.getPersonneContact().getId())
//				.orElseThrow(() -> new NotExistException(newFichier.getPersonneContact().getId().toString()));
//
//		if (fichierDto.getId().equals(id)) {
//			return ResponseEntity.ok(fichierService.update(fichierDto.toFichier(persC)));
//		}
//		
//		return ResponseEntity.notFound().build();
//	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
//		if (fichierService.existsById(id)) {
//			fichierService.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		
//		return ResponseEntity.notFound().build();
//	}


}
