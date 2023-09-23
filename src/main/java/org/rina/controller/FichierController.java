package org.rina.controller;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.FichierDto;
import org.rina.dto.response.FichierResponseDto;
import org.rina.enums.Roles;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.rina.model.User;
import org.rina.service.FichierServices;
import org.rina.service.PersonneContactServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/file")
public class FichierController {

	// Spécifie l'emplacement de stockage des fichiers
	@Value("${upload.directory}")
	private String uploadDirectory;

	@Autowired
	private FichierServices fichierService;
	@Autowired
	private PersonneContactServices persCService;
	@Autowired
	private UserService userService;

	/**
	 * Récupérer tous les fichiers de l'établissement ou d'une personne.
	 */
	@GetMapping("/liste/{username}")
	public ResponseEntity<List<FichierResponseDto>> getAllFiles(@PathVariable String username) {
		Optional<User> existingUser = userService.findByUsername(username);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			// Vérifier le rôle de l'utilisateur
			if (user.getRole() == Roles.PERSONNECONTACT) {
				// Si l'utilisateur est une personne de contact, Vérifier si la personne existe
				Optional<PersonneContact> existingPersonC = persCService.findByUsername(username);
				if (existingPersonC.isPresent()) {
					// Retourner la liste des fichiers de la personne
					PersonneContact personC = existingPersonC.get();
					List<Fichier> filesId = fichierService.findAllByPersonneContactOrderByDateDesc(personC.getId());
					// Mapper les fichiers en DTO
					List<FichierResponseDto> fileDtos = filesId.stream().map(FichierResponseDto::new)
							.collect(Collectors.toList());
					return ResponseEntity.ok(fileDtos);
				}
			} 
			else if (user.getRole() == Roles.ETABLISSEMENT || (user.getRole() == Roles.ADMIN)) {
				// Si l'utilisateur est un établissement ou un administrateur
				// Récupérer la liste de tous les fichiers triés par date décroissante
				List<Fichier> files = fichierService.findAllOrderByDateDesc();
				// Mapper les fichiers en DTO
				List<FichierResponseDto> fileDtos = files.stream().map(FichierResponseDto::new)
						.collect(Collectors.toList());
				return ResponseEntity.ok(fileDtos);
			}
		}
		// Renvoyer une réponse 404 si l'utilisateur ou la personne de contact n'existe  pas
		return ResponseEntity.notFound().build();
	}

	/**
	 * Récupérer un fichier par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<FichierResponseDto> getFileById(@PathVariable Long id) {
		Optional<Fichier> existingFichier = fichierService.findById(id);
		if (existingFichier.isPresent()) {
			// Récupérer le fichier correspondant à l'ID
			Fichier fichier = existingFichier.get();
			// Mapper le fichier en DTO
			FichierResponseDto fileResponseDto = new FichierResponseDto(fichier);
			return ResponseEntity.ok(fileResponseDto);
		} else {
			// Renvoyer une réponse 404 si le fichier n'existe pas
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Créer ou Téléverser un fichier.
	 */
	@PostMapping("/{username}")
	public ResponseEntity<String> createFile(@Valid @RequestBody FichierDto fichierDto, @PathVariable String username) {
		Optional<User> existingUser = userService.findByUsername(username);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			// Vérifier si l'utilisateur est une personne de contact
			if (user.getRole() == Roles.PERSONNECONTACT) {
				// Récupérer la personne de contact correspondante
				PersonneContact persC = persCService.findByUsername(username)
						.orElseThrow(() -> new NotExistException(username));

				try {
					// Lire le contenu du fichier depuis MultipartFile
					byte[] contenu = fichierDto.getFichierIn().getBytes();

					// Générer un nom de fichier unique
					String extensionUnique = fichierService
							.getExtension(fichierDto.getFichierIn().getOriginalFilename());
					String nomFichierUnique = fichierService.generateUniqueFileName(extensionUnique);

					// Définir le chemin de stockage complet sur le serveur
					String cheminStockage = uploadDirectory + File.separator + nomFichierUnique;

					// Enregistrer le contenu du fichier sur le serveur
					saveFileToServer(contenu, cheminStockage);

					// Créer un objet Fichier avec les données
					Fichier file = new Fichier(null, fichierDto.getTypeF(), fichierDto.getDate(), cheminStockage,
							contenu, persC);

					// Enregistrer le fichier dans la base de données
					fichierService.insert(file);

					// Renvoyer une réponse 200 si la création du fichier est réussie
					return ResponseEntity.ok().build();
				} catch (IOException e) {
					e.printStackTrace();
					// Renvoyer une réponse d'erreur interne du serveur en cas d'échec de création
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("Erreur lors du traitement du fichier.");
				}
			}
		}

		// Renvoyer une réponse 400 si l'utilisateur n'est pas une personne de contact
		// ou si le fichier existe déjà
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Le fichier existe déjà ou l'utilisateur n'est pas une personne de contact.");
	}

	/**
	 * Télécharger un fichier.
	 */
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		if (fileName.isEmpty()) {
			// Renvoyer une réponse d'erreur interne du serveur si le nom de fichier est
			// vide
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		try {
			// Créez un chemin complet vers le fichier à partir du nom de fichier
			String filePath = uploadDirectory + File.separator + fileName;

			// Chargez le fichier en tant que ressource
			Resource resource = new UrlResource(Paths.get(filePath).toUri());

			if (resource.exists() && resource.isReadable()) {
				// Renvoyer le fichier en tant que téléchargement
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
			} else {
				// Renvoyer une réponse 404 si le fichier n'existe pas ou n'est pas lisible
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Renvoyer une réponse d'erreur interne du serveur en cas d'échec du
			// téléchargement
			return ResponseEntity.internalServerError().build();
		}
	}

	private void saveFileToServer(byte[] contenu, String cheminStockage) {
		try {
			// Écrire le contenu du fichier sur le serveur
			Path filePath = Paths.get(cheminStockage);
			Files.write(filePath, contenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
