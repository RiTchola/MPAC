package org.rina.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.ActiviteDto;
import org.rina.dto.response.ActiviteResponseDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.model.Activite;
import org.rina.model.Etablissement;
import org.rina.service.ActiviteServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

	@Autowired
	private ActiviteServices activiteService;
	@Autowired
	private EtablissementServices etablissementService;

	/**
	 * Récupérer tous les activités.
	 */
	@GetMapping
	public ResponseEntity<List<ActiviteResponseDto>> getAllActivity() {
		// Récupèrer la liste de toutes les activités
		List<Activite> activities = activiteService.findAll();

		// Convertir la liste d'activités en liste d'ActiviteResponseDto
		List<ActiviteResponseDto> actDtoList = activities.stream()
				.map(ActiviteResponseDto::new)
				.collect(Collectors.toList());
		// Renvoyer la liste des activités en réponse
		return ResponseEntity.ok(actDtoList);
	}

	/**
	 * Récupérer une activité par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ActiviteResponseDto> getActivityById(@PathVariable Long id) {
		// Rechercher une activité par son ID
		Optional<Activite> existingActivite = activiteService.findById(id);

		// Si l'activité existe, renvoie-la en réponse sous forme d'ActiviteResponseDto
		if (existingActivite.isPresent()) {
			Activite activite = existingActivite.get();
			ActiviteResponseDto actDto = new ActiviteResponseDto(activite);
			return ResponseEntity.ok(actDto);
		}

		// Renvoyer une réponse 200 si l'activité n'est pas trouvée
		return ResponseEntity.ok().build();
	}

	/**
	 * Créer une nouvelle activité.
	 */
	@PostMapping
	public ResponseEntity<ActiviteResponseDto> createActivity(@Valid @RequestBody ActiviteDto actDto) {
		// Obtenir l'établissement associé à l'activité
		Long idEtab = etablissementService.getEtablissementId();
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		// Créer une nouvelle activité et Insère la nouvelle activité dans la base de données
		Activite newActivite = activiteService.insert(actDto.toActivite(etab));

		// Créer un DTO de réponse pour la nouvelle activité
		ActiviteResponseDto actResponseDto = new ActiviteResponseDto(newActivite);
		return ResponseEntity.ok(actResponseDto);
	}

	/**
	 * Mettre à jour une activité existante.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Activite> updateActivity(@PathVariable Long id, @Valid @RequestBody ActiviteDto actDto) {
		// Vérifier si l'activité existe en fonction de l'ID
		Optional<Activite> existingActivite = activiteService.findById(id);

		if (existingActivite.isPresent()) {
			// Obtenir l'établissement associé à l'activité
			Long idEtab = etablissementService.getEtablissementId();
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Mettre à jour l'activité existante avec les nouvelles valeurs
			actDto.setId(id);
			Activite updateActivite = activiteService.updateActivite(id, actDto.toActivite(etab));

			// Renvoyer une réponse avec l'activité mise à jour
			return ResponseEntity.ok(updateActivite);
		} 
		else {
			// Renvoyer une réponse 200 si l'activité n'est pas trouvée
			return ResponseEntity.ok().build();
		}
	}

	/**
	 * Supprimer une activité par son ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDto> deleteActivity(@PathVariable Long id) {
		// Vérifier si l'activité existe en fonction de l'ID
		if (activiteService.existsById(id)) {
			// Supprimer l'activité si elle existe
			activiteService.deleteById(id);

			// Renvoyer une réponse HTTP indiquant le succès de l'opération
			String msg = "Activité supprimée avec succès";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		} 
		else {
			// Renvoyer une réponse HTTP indiquant le défaut de l'opération
			String msg = "L'activité n'existe pas";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		}
	}
}