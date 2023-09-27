package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EvenementDto;
import org.rina.dto.response.EvenementResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.rina.service.EtablissementServices;
import org.rina.service.EvenementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evenement")
public class EvenementController {

	@Autowired
	private EvenementServices evenementService;
	@Autowired
	private EtablissementServices etablissementService;

	/**
	 * Récupérer tous les événements.
	 */
	@GetMapping
	public ResponseEntity<List<EvenementResponseDto>> getAllEvents() {
		// Récupèrer la liste de tous les événements
		List<Evenement> evenements = evenementService.findAll();

		// Convertir la liste d'événements en une liste de DTO de réponse
		List<EvenementResponseDto> eventResponseDtoList = evenements.stream()
				.map(EvenementResponseDto::new)
				.collect(Collectors.toList());

		return ResponseEntity.ok(eventResponseDtoList);
	}

	/**
	 * Récupérer un événement par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<EvenementResponseDto> getEventById(@PathVariable Long id) {
		// Rechercher un événement par son ID
		Optional<Evenement> existingEvenement = evenementService.findById(id);

		if (existingEvenement.isPresent()) {
			// Si l'événement existe, créer un DTO de réponse pour l'événement
			Evenement event = existingEvenement.get();
			EvenementResponseDto eventResponseDto = new EvenementResponseDto(event);
			return ResponseEntity.ok(eventResponseDto);
		}

		// Si l'événement n'est pas trouvé, renvoyer une réponse 404 (non trouvée)
		return ResponseEntity.notFound().build();
	}

	/**
	 * Créer un nouveau événement.
	 */
	@PostMapping
	public ResponseEntity<EvenementResponseDto> createEvent(@Valid @RequestBody EvenementDto evenementDto) {
		// Obtenir l'ID de l'établissement associé à l'événement
		Long idEtab = Long.valueOf(1);

		// Recherche l'établissement par son ID
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		// Créer un nouvel événement et Insèrer le nouvel événement dans la base de données
		Evenement newEvenement = evenementService.insert(evenementDto.toEvenement(etab));

		// Créer un DTO de réponse pour le nouvel événement
		EvenementResponseDto eventResponseDto = new EvenementResponseDto(newEvenement);
		return ResponseEntity.ok(eventResponseDto);
	}

	/**
	 * Mettre à jour un événement existant.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<EvenementResponseDto> updateEvent(@PathVariable Long id,
			@Valid @RequestBody EvenementDto evenementDto) {
		// Rechercher l'événement existant par son ID
		Optional<Evenement> existingEvenement = evenementService.findById(id);

		if (existingEvenement.isPresent()) {
			// Obtenir l'ID de l'établissement associé à l'événement (dans cet exemple, l'ID
			// est 1)
			Long idEtab = Long.valueOf(1);

			// Rechercher l'établissement par son ID
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Mettre à jour l'événement existant avec les nouvelles valeurs
			evenementDto.setId(id);
			Evenement updatedEvenement = evenementService.updateEvenement(id, evenementDto.toEvenement(etab));

			// Créer un DTO de réponse pour l'événement mis à jour
			EvenementResponseDto eventResponseDto = new EvenementResponseDto(updatedEvenement);
			return ResponseEntity.ok(eventResponseDto);
		} else {
			// Si l'événement n'est pas trouvé, renvoyer une réponse 404 (non trouvée)
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Supprimer un événement par son ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
		// Vérifie si l'évenement existe en fonction de l'ID
		if (evenementService.existsById(id)) {
			// Si l'événement existe, le supprime
			evenementService.deleteById(id);

			// Renvoie une réponse HTTP indiquant le succès de l'opération
			return ResponseEntity.status(HttpStatus.OK).body("Évenement supprimée avec succès");
		} else {
			// Renvoyer une réponse 404 si la personne de contact n'existe pas
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'évenement n'existe pas");
		}
	}

}