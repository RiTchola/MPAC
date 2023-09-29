package org.rina.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.EvenementDto;
import org.rina.dto.response.EvenementResponseDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.model.Etablissement;
import org.rina.model.Evenement;
import org.rina.service.EtablissementServices;
import org.rina.service.EvenementServices;
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

		// Renvoyer une réponse 200 si l'événement n'est pas trouvé
		return ResponseEntity.ok().build();
	}

	/**
	 * Créer un nouveau événement.
	 */
	@PostMapping
	public ResponseEntity<EvenementResponseDto> createEvent(@Valid @RequestBody EvenementDto evenementDto) {
		// Obtenir l'ID de l'établissement associé à l'événement
		Long idEtab = etablissementService.getEtablissementId();

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
			// Obtenir l'établissement associé à l'évènement
			Long idEtab = etablissementService.getEtablissementId();
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Mettre à jour l'événement existant avec les nouvelles valeurs
			evenementDto.setId(id);
			Evenement updatedEvenement = evenementService.updateEvenement(id, evenementDto.toEvenement(etab));

			// Créer un DTO de réponse pour l'événement mis à jour
			EvenementResponseDto eventResponseDto = new EvenementResponseDto(updatedEvenement);
			return ResponseEntity.ok(eventResponseDto);
		} 
		else {
			// Renvoyer une réponse 200 si l'événement n'est pas trouvé
			return ResponseEntity.ok().build();
		}
	}

	/**
	 * Supprimer un événement par son ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDto> deleteEvent(@PathVariable Long id) {
		// Vérifie si l'évenement existe en fonction de l'ID
		if (evenementService.existsById(id)) {
			// Si l'événement existe, le supprime
			evenementService.deleteById(id);

			// Renvoie une réponse HTTP indiquant le succès de l'opération
			String msg = "Évenement supprimée avec succès";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		}
		else {
			// Renvoyer une réponse HTTP indiquant le défaut de l'opération
			String msg = "L'évenement n'existe pas";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		}
	}

}