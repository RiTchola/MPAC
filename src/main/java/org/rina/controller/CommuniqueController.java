package org.rina.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.CommuniqueDto;
import org.rina.dto.response.CommuniqueResponseDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.files.FilesStorageService;
import org.rina.model.Communique;
import org.rina.model.Etablissement;
import org.rina.service.CommuniqueServices;
import org.rina.service.EtablissementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/communique")
@RequiredArgsConstructor
public class CommuniqueController {

	private final CommuniqueServices communiqueService;
	private final EtablissementServices etablissementService;
	private final FilesStorageService filesStorageService;

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
            // Renvoyer une réponse 200 si le communiqué n'existe pas
            return ResponseEntity.ok().build();
        }
    }

	/**
	 * Créer un nouveau communiqué.
	 */
	@PostMapping
	public ResponseEntity<Communique> createCommunique(
			@RequestParam("contenu")String contenu,
			@RequestParam("titre")String titre,
		    @RequestParam("date")String date,
			@RequestParam("files") MultipartFile...files) {
		// Obtient l'établissement associé au communiqué
		Long idEtab = etablissementService.getEtablissementId();
		Etablissement etab = etablissementService.findById(idEtab)
				.orElseThrow(() -> new NotExistException(idEtab.toString()));


		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Communique communique = Communique.builder()
					.date(formatter.parse(date))
					.titre(titre)
					.contenu(contenu)
					.etablissement(etab).build();
			List<String> fileURL = new ArrayList<>();
			for (MultipartFile file : files) {
				String url = filesStorageService.saveFile(file);
				fileURL.add(url);
			}
			communique.setFileURL(fileURL);
			return  ResponseEntity.ok(communiqueService.insert(communique));
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

		// Crée et insère le communiqué
		//Communique newCom = communiqueService.insert(comDto.toCommunique(etab));

		// Crée une CommuniqueResponseDto à partir du communiqué créé
		//CommuniqueResponseDto comResponse = new CommuniqueResponseDto(newCom);

		// Renvoie la réponse avec le communiqué créé au format CommuniqueResponseDto
		//return ResponseEntity.ok(comResponse);
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
			Long idEtab = etablissementService.getEtablissementId();
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
			// Renvoyer une réponse 200 si le communiqué n'existe pas
            return ResponseEntity.ok().build();
		}
	}

	/**
	 * Supprimer un communiqué par son ID.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDto> deleteCommunique(@PathVariable Long id) {
		// Vérifie si le communiqué existe en fonction de l'ID
		if (communiqueService.existsById(id)) {
			// Supprime le communiqué s'il existe
			communiqueService.deleteById(id);
			
			// Renvoyer une réponse HTTP indiquant le succès de l'opération
			String msg = "Communiqué supprimée avec succès";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		} 
		else {
			// Renvoyer une réponse HTTP indiquant le défaut de l'opération
			String msg = "Le communique n'existe pas";
			return ResponseEntity.ok(new MessageResponseDto(msg));
		}
	}
}
