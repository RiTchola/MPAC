package org.rina.controller;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.CommuniqueDto;
import org.rina.dto.response.CommuniqueResponseDto;
import org.rina.model.Communique;
import org.rina.model.Etablissement;
import org.rina.service.CommuniqueServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/communique")
public class CommuniqueController {

	@Autowired
	private CommuniqueServices communiqueService;
	@Autowired
	private EtablissementServices etablissementService;

	/**
     * Récupérer tous les communiqués.
     */
    @GetMapping
    public ResponseEntity<List<CommuniqueResponseDto>> getAllCommunique() {
        // Récupérer de la liste de tous les communiqués triés par date décroissante
        List<Communique> communiques = communiqueService.findAllCommuniqueOrderByDateDesc();

        // Convertir la liste de Communiqués en une liste de CommuniqueResponseDto
        List<CommuniqueResponseDto> communiquesResponse = communiques.stream()
                .map(CommuniqueResponseDto::new)
                .collect(Collectors.toList());

        // Renvoyer de la liste des communiqués en réponse
        return ResponseEntity.ok(communiquesResponse);
    }

    /**
     * Récupérer un communiqué par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommuniqueResponseDto> getCommuniqueById(@PathVariable Long id) {
        // Rechercher d'un communiqué par son ID
        Optional<Communique> existingCom = communiqueService.findById(id);

        // Si le communique existe, renvoie-la en réponse sous forme de CommuniqueResponseDto
        if (existingCom.isPresent()) {
            Communique com = existingCom.get();
            CommuniqueResponseDto comResponse = new CommuniqueResponseDto(com);
            return ResponseEntity.ok(comResponse);
        } 
        else {
            // Renvoyer une réponse 404 si le communiqué n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

	/**
	 * Créer un nouveau communiqué.
	 */
	@PostMapping
	public ResponseEntity<CommuniqueResponseDto> createCommunique(@Valid @RequestBody CommuniqueDto comDto) {
		// Obtient l'établissement associé au communiqué
		Long idEtab = Long.valueOf(1);
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));

		// Crée et insère le communiqué
		Communique newCom = communiqueService.insert(comDto.toCommunique(etab));

		// Crée une CommuniqueResponseDto à partir du communiqué créé
		CommuniqueResponseDto comResponse = new CommuniqueResponseDto(newCom);

		// Renvoie la réponse avec le communiqué créé au format CommuniqueResponseDto
		return ResponseEntity.ok(comResponse);
	}

	/**
	 * Mettre à jour un communiqué existant.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<CommuniqueResponseDto> updateCommunique(@PathVariable Long id,
			@Valid @RequestBody CommuniqueDto comDto) {
		// Vérifie d'abord si le communiqué existe en fonction de l'ID
		Optional<Communique> existingCommunique = communiqueService.findById(id);

		if (existingCommunique.isPresent()) {
			// Obtient l'établissement associé au communiqué
			Long idEtab = Long.valueOf(1);
			Etablissement etab = etablissementService.findById(idEtab)
					.orElseThrow(() -> new NotExistException(idEtab.toString()));

			// Met à jour le communiqué existant avec les nouvelles valeurs
			comDto.setId(id);
			Communique updateCom = communiqueService.updateCommunique(id, comDto.toCommunique(etab));

			// Crée une CommuniqueResponseDto à partir du communiqué mis à jour
			CommuniqueResponseDto comResponse = new CommuniqueResponseDto(updateCom);

			// Renvoie la réponse avec le communiqué mis à jour au format CommuniqueResponseDto
			return ResponseEntity.ok(comResponse);
		} 
		else {
			// Si le communiqué n'existe pas, renvoie une réponse 404 (non trouvé)
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Supprimer un communiqué par son ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCommunique(@PathVariable Long id) {
		// Vérifie si le communiqué existe en fonction de l'ID
		if (communiqueService.existsById(id)) {
			// Supprime le communiqué s'il existe
			communiqueService.deleteById(id);
			
			// Renvoyer une réponse HTTP indiquant le succès de l'opération
			return ResponseEntity.status(HttpStatus.OK).body("Communiqué supprimée avec succès");
		} 
		else {
			// Renvoyer une réponse 404 si le communiqué n'existe pas
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le communique n'existe pas");
		}
	}
}
