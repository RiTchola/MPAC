package org.rina.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.rina.controller.exceptions.NotExistException;
import org.rina.dto.response.FichierResponseDto;
import org.rina.dto.response.MessageResponseDto;
import org.rina.enums.Roles;
import org.rina.enums.TypeFichier;
import org.rina.model.Fichier;
import org.rina.model.PersonneContact;
import org.rina.model.User;
import org.rina.service.FichierServices;
import org.rina.service.FilesStorageService;
import org.rina.service.PersonneContactServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FichierController {

    @Autowired
    private FilesStorageService filesStorageService;
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
            } else if (user.getRole() == Roles.ETABLISSEMENT || (user.getRole() == Roles.ADMIN)) {
                // Si l'utilisateur est un établissement ou un administrateur
                // Récupérer la liste de tous les fichiers triés par date décroissante
                List<Fichier> files = fichierService.findAllOrderByDateDesc();
                // Mapper les fichiers en DTO
                List<FichierResponseDto> fileDtos = files.stream().map(FichierResponseDto::new)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(fileDtos);
            }
        }
        // Renvoyer une réponse 200 si l'utilisateur ou la personne de contact n'existe
        // pas
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<MessageResponseDto> addFile(@RequestParam("date") String date, @RequestParam("destinataire") String destinataire,
                                                      @RequestParam("typeF") TypeFichier typeF, @RequestParam("username") String username,
                                                      @RequestParam("file") MultipartFile file) {

        Optional<User> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getRole() == Roles.PERSONNECONTACT) {
                PersonneContact persC = persCService.findByUsername(username)
                        .orElseThrow(() -> new NotExistException(username));

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                    Fichier fichier = Fichier.builder().date(formatter.parse(date)).destinataire(destinataire)
                            .fileURL(filesStorageService.saveFile(file)).personneContact(persC).typeF(typeF).build();
                    fichierService.insert(fichier);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }
            } else {
                // c est pas la personne de contact
                System.out.println("No contact Personne");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }

        } else {
            System.out.println("No username");
            // le username n'existe pas
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        String msg = "Fichier téléversé avec succès";
        return ResponseEntity.ok(new MessageResponseDto(msg));
    }

    /**
     * Télécharger un fichier.
     */
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = filesStorageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);


    }

}
