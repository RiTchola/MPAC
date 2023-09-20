package org.rina.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.request.FichierDto;
import org.rina.enums.Roles;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.rina.service.FichierServices;
import org.rina.service.PersonneContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

	/**
	 * Récupérer tous les fichiers de l'établissement.
	 */
	@GetMapping
	public ResponseEntity<List<Fichier>> getAllFile() {
		List<Fichier> files = fichierService.findAllOrderByDateDesc();
		return ResponseEntity.ok(files);
	}

	/**
	 * Récupérer tous les fichiers d'une personne.
	 */
	@GetMapping("/personneC")
	public ResponseEntity<List<Fichier>> getAllFileByPersonneContact(Authentication auth) {
		if (auth != null || hasRole(auth, Roles.PERSONNECONTACT)) {
			// vérifie si la personne existe
			Optional<PersonneContact> existingPersonC = persCService.findByUsername(auth.getName());
			if (existingPersonC.isPresent()) {

				// Retourne sa liste de fichiers
				PersonneContact personC = existingPersonC.get();
				List<Fichier> filesId = fichierService.findAllByPersonneContactOrderByDateDesc(personC.getId());
				return ResponseEntity.ok(filesId);
			}
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Récupérer un fichier par son ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Fichier> getFileById(@PathVariable Long id) {
		Optional<Fichier> fichier = fichierService.findById(id);
		if (fichier.isPresent()) {
			return ResponseEntity.ok(fichier.get());
		}

		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Créer un nouveau fichier.
	 */
	@SuppressWarnings("unused")
	@PostMapping
	public ResponseEntity<String> createFile(@Valid @RequestBody FichierDto fichierDto, Authentication auth) {
		if (auth != null || hasRole(auth, Roles.PERSONNECONTACT)) {
			PersonneContact persC = persCService.findByUsername(auth.getName())
					.orElseThrow(() -> new NotExistException(auth.getName()));

			try {
				// Lire le contenu du fichier depuis MultipartFile
				byte[] contenu = fichierDto.getFichierIn().getBytes();

				// Générer un nom de fichier unique
				String extensionUnique = fichierService.getExtension(fichierDto.getFichierIn().getOriginalFilename());
				String nomFichierUnique = fichierService.generateUniqueFileName(extensionUnique);

				// Définir le chemin de stockage complet sur le serveur
				String cheminStockage = uploadDirectory + File.separator + nomFichierUnique;

				// Enregistrer le contenu du fichier sur le serveur
				saveFileToServer(contenu, cheminStockage);

				// Créer un objet Fichier avec les données
				Fichier file = new Fichier(null, fichierDto.getTypeF(), fichierDto.getDate(), cheminStockage, contenu,
						persC);

				// Enregistrer le fichier dans la base de données
				fichierService.insert(file);

				return ResponseEntity.ok().build();
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement du fichier.");
			}
		}

		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier existe déjà.");
	}


	/**
	 * Télecharger un fichier.
	 * 
	 */
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		if (fileName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		try {
			// Créez un chemin complet vers le fichier à partir du nom de fichier
			String filePath = uploadDirectory + File.separator + fileName;

			// Chargez le fichier en tant que ressource
			Resource resource = new UrlResource(Paths.get(filePath).toUri());

			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
			} else return ResponseEntity.notFound().build();
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * Petite méthode privée qui vérifie si l'objet auth possède le role désigné
	 * 
	 * @param auth
	 * @param role
	 * @return vrai s'il possède le role
	 */
	private boolean hasRole(Authentication auth, Roles role) {
		String roleStr = role.name();
		return auth.getAuthorities().stream().anyMatch(a -> roleStr.equals(a.getAuthority()));
	}

	private void saveFileToServer(byte[] contenu, String cheminStockage) {
        try {
        	Path filePath = Paths.get(cheminStockage);
            Files.write(filePath, contenu);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
