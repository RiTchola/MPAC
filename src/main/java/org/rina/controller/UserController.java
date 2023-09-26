package org.rina.controller;

import java.util.Optional;

import javax.security.auth.login.CredentialException;

import org.rina.config.JwtService;
import org.rina.dto.request.ChangePasswordDto;
import org.rina.dto.request.CredentialValidation;
import org.rina.dto.request.UserDto;
import org.rina.enums.Roles;
import org.rina.model.PersonneContact;
import org.rina.model.User;
import org.rina.service.PersonneContactServices;
import org.rina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userSrv;
    private final JwtService jwtService;
    private final PasswordEncoder encodeur;
    @Autowired
    private PersonneContactServices personService;
    

    /**
     * Récupérer un username.
     */
    @GetMapping
    public ResponseEntity<User> getUsername(HttpServletRequest userDto) {
        // Obtenir le token d'authentification à partir de l'en-tête de la requête
        String token = userDto.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            // Renvoyer une réponse 403 si le token est manquant ou invalide
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Extraire le nom d'utilisateur à partir du token JWT
        String utilToken = token.substring(7);
        String currentUsername = jwtService.extractUsername(utilToken);
        if (currentUsername != null) {
            // Récupérer l'utilisateur associé au nom d'utilisateur extrait
            User user = userSrv.findByUsername(currentUsername).orElseThrow();
            // Renvoyer l'utilisateur en réponse
            return ResponseEntity.ok(user);
        }
        // Renvoyer une réponse 403 si le nom d'utilisateur n'est pas trouvé dans le token
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Récupérer un user par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Vérifier si un utilisateur existe pour l'ID spécifié
        Optional<User> user = userSrv.findById(id);
        if (user.isPresent()) {
            // Renvoyer l'utilisateur en réponse
            return ResponseEntity.ok(user.get());
        } else {
            // Renvoyer une réponse 404 si l'utilisateur n'existe pas
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Créer un nouveau user.
     */
	@PostMapping
    public ResponseEntity<String> createUser(@Validated(CredentialValidation.class) @RequestBody UserDto userDto) throws CredentialException {
        // Vérifier si l'utilisateur existe déjà en fonction du nom d'utilisateur
        Optional<User> exitingUser = userSrv.findByUsername(userDto.getUsername());

        if (exitingUser.isEmpty()) {
        	if (userDto.getRole() == Roles.PERSONNECONTACT) {
			    // Vérifier si la personne de contact correspondante existe
			    Optional<PersonneContact> existingPersC = personService.findByUsername(userDto.getUsername());
			    if (existingPersC.isPresent()) {
			        // Associer la personne de contact au nouvel utilisateur
			        PersonneContact persC = existingPersC.get();
			        User newUser = userSrv.insert(userDto.toUser(encodeur));
			        persC.setUser(newUser);
			        // Renvoyer une réponse 200 si la création de l'utilisateur est réussie
			        ResponseEntity.status(HttpStatus.OK).body(newUser.getUsername());
			    } else {
			        // Renvoyer une réponse 400 si la personne de contact n'existe pas encore
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La personne n'existe pas encore.");
			    }
			}
			// Créer un nouvel utilisateur dans le cas général
			User newUser = userSrv.insert(userDto.toUser(encodeur));
			// Renvoyer une réponse 200 si la création de l'utilisateur est réussie
			return ResponseEntity.status(HttpStatus.OK).body(newUser.getUsername()); 
        } else {
            // Renvoyer une réponse 400 si l'utilisateur existe déjà
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur existe déjà.");
        }
    }

    /**
     * Demander un nouveau mot de passe.
     */
    @GetMapping("/{username}/newPassword")
    public ResponseEntity<String> newPassword(@PathVariable String username) {
        // Vérifie d'abord si l'utilisateur existe en fonction du username
        Optional<User> existingUser = userSrv.findByUsername(username);

        if (existingUser.isPresent()) {
            // Récupérer l'utilisateur existant
            User user = existingUser.get();
            // Renvoyer la réponse avec l'utilisateur
            return ResponseEntity.status(HttpStatus.OK).body("Un nouveau mot de passe a été envoyée à l'adresse "+ user.getUsername());
        } else {
            // Renvoyer une réponse 404 si l'utilisateur n'existe pas
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le compte n'existe pas");
        }
    }
    
    /**
     * Mettre à jour un mot de passe.
     */
    @PutMapping("/{username}/update")
    public ResponseEntity<String> changePWPut(@PathVariable String username,
            @Validated(CredentialValidation.class) @RequestBody ChangePasswordDto userCPwDto, Authentication auth, Model model) {
        // Vérifier si l'utilisateur existe en fonction du nom d'utilisateur
        Optional<User> existingUser = userSrv.findByUsername(username);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // Changer le mot de passe de l'utilisateur
            try {
                userSrv.changePassword(user, userCPwDto.getOldPassword(), userCPwDto.getPassword());
                // Renvoyer une réponse 202 si le mot de passe est changé avec succès
                return ResponseEntity.status(HttpStatus.OK).body("Mot de passe changé avec succès");
            } catch (CredentialException e) {
            	// Récupérer le message d'erreur de l'exception
                String errorMessage = e.getMessage();

                // Renvoyer une réponse 400 avec le message d'erreur
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        }

     // Renvoyer une réponse 404 si l'utilisateur n'existe pas
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le compte n'existe pas");
    }

}
