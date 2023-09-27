package org.rina.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.MeetUpDto;
import org.rina.dto.response.MeetUpResponseDto;
import org.rina.enums.Roles;
import org.rina.model.Etablissement;
import org.rina.model.MeetUp;
import org.rina.model.PersonneContact;
import org.rina.model.User;
import org.rina.service.EtablissementServices;
import org.rina.service.MeetUpServices;
import org.rina.service.PersonneContactServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/meetup")
public class MeetUpController {

	@Autowired
	private MeetUpServices meetUpService;
	@Autowired
	private EtablissementServices etablissementService;
	@Autowired
	private PersonneContactServices persCService;
	@Autowired
	private UserService userService;

	/**
	 * Récupérer tous les meetUps de l'établissement ou d'une personne.
	 */
	@GetMapping("/liste/{username}")
	public ResponseEntity<List<MeetUpResponseDto>> getAllMeetUps(@PathVariable String username) {
		// Vérifier si l'utilisateur existe
		Optional<User> existingUser = userService.findByUsername(username);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			
			// Vérifier le rôle de l'utilisateur
			if (user.getRole() == Roles.PERSONNECONTACT) {
				// Si l'utilisateur est une personne de contact, récupérer ses meetUps
				Optional<PersonneContact> existingPersonC = persCService.findByUsername(username);
				if (existingPersonC.isPresent()) {
					PersonneContact personC = existingPersonC.get();
					
					// Récupérer la liste des meetUps de la personne de contact triés par date décroissante
					List<MeetUp> meetUpsId = meetUpService.findAllMeetByIdOrderByDateDesc(personC.getId());
					List<MeetUpResponseDto> meetUpResponseDtos = meetUpsId.stream()
							.map(MeetUpResponseDto::new)
							.collect(Collectors.toList());
					// Renvoyer la liste des meetUps en réponse
					return ResponseEntity.ok(meetUpResponseDtos);
				}
			} 
			else if (user.getRole() == Roles.ETABLISSEMENT || user.getRole() == Roles.ADMIN) {
				
				// Si l'utilisateur est un établissement ou un administrateur
				// Récupérer la liste de tous les meetUps triés par date décroissante
				List<MeetUp> meetUps = meetUpService.findAllMeetOrderByDateDesc();
				List<MeetUpResponseDto> meetUpResponseDtos = meetUps.stream().map(MeetUpResponseDto::new)
						.collect(Collectors.toList());
				// Renvoyer la liste des meetUps en réponse
				return ResponseEntity.ok(meetUpResponseDtos);
			}
		}
		// Renvoyer une réponse 404 si l'utilisateur n'existe pas ou ne correspond pas à un rôle valide
		return ResponseEntity.notFound().build();
	}

	/**
	 * Récupérer un meetUp par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<MeetUp> getMeetUpById(@PathVariable Long id) {
		// Vérifier si le meetUp avec l'ID donné existe
		Optional<MeetUp> meetUp = meetUpService.findById(id);
		
		if (meetUp.isPresent()) {
			// Renvoyer le meetUp en réponse
			return ResponseEntity.ok(meetUp.get());
		} else {
			// Renvoyer une réponse 404 si le meetUp n'existe pas
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Créer un nouveau meetUp.
	 */
	@PostMapping("/{username}")
	public ResponseEntity<String> createMeetUp(@PathVariable String username, @Valid @RequestBody MeetUpDto meetUpDto) {
		// Vérifier si l'utilisateur existe
		Optional<User> existingUser = userService.findByUsername(username);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			
			// Vérifier le rôle de l'utilisateur
			if (user.getRole() == Roles.PERSONNECONTACT) {
				// Récupérer la personne de contact correspondante
				PersonneContact persC = persCService.findByUsername(username)
						.orElseThrow(() -> new NotExistException(username));
				Long idEtab = Long.valueOf(1);
				// Récupérer l'établissement associé au meetUp
				Etablissement etab = etablissementService.findById(idEtab)
						.orElseThrow(() -> new NotExistException(idEtab.toString()));
				
				// Créer et insérer le meetUp dans la base de données
				@SuppressWarnings("unused")
				MeetUp meetUp = meetUpService.insert(meetUpDto.toMeetUp(etab, persC));
				// Renvoyer une réponse 200 si la création du meetUp est réussie
				return ResponseEntity.status(HttpStatus.OK).body("Demande soumise avec succès.");
			} 
			else {
				// Renvoyer une réponse 400 si la création du meetUp n'est pas autorisée pour ce rôle
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Demande impossible.");
			}
		} 
		
		else {
			// Renvoyer une réponse 400 si l'utilisateur n'existe pas
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur n'existe pas.");
		}
	}

	/**
	 * Mettre à jour un meetUp existant.
	 */
	@PutMapping("/{id}/{username}")
	public ResponseEntity<MeetUpResponseDto> updateMeetUp(@PathVariable("id") Long id,
			@PathVariable("username") String username, @Valid @RequestBody MeetUpDto meetUpDto) {
		// Vérifie d'abord si le meetUp existe en fonction de l'ID
		Optional<MeetUp> existingMeetUp = meetUpService.findById(id);

		if (existingMeetUp.isPresent()) {
			MeetUp meetUp = existingMeetUp.get();
			Optional<User> existingUser = userService.findByUsername(username);
			if (existingUser.isPresent()) {
				User user = existingUser.get();
				
				// Vérifier le rôle 
				if (user.getRole() == Roles.ETABLISSEMENT || user.getRole() == Roles.ADMIN) {
					// Récupérer la personne de contact correspondante
					PersonneContact persC = meetUp.getPersonneContact();
					Long idEtab = Long.valueOf(1);
					// Récupérer l'établissement associé au meetUp
					Etablissement etab = etablissementService.findById(idEtab)
							.orElseThrow(() -> new NotExistException(idEtab.toString()));
					
					// Mise à jour du meetUp existant avec les nouvelles valeurs
					meetUpDto.setId(id);
					MeetUp updateMeetUp = meetUpService.updateMeetUp(id, meetUpDto.toMeetUp(etab, persC));
					// Renvoyer une réponse 200 avec le meetUp mis à jour
					return ResponseEntity.ok(new MeetUpResponseDto(updateMeetUp));
				}
				else {
					// Renvoyer une réponse 404 si le role de l'utilisateur n'est pas celui souhaité
					return ResponseEntity.notFound().build();
				}
			}
		}
		// Renvoyer une réponse 404 si le meetUp n'existe pas 
		return ResponseEntity.notFound().build();
	}
}