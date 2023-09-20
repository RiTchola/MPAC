package org.rina.controller;

import java.util.Optional;

import javax.security.auth.login.CredentialException;

import org.rina.config.JwtService;
import org.rina.dto.request.ChangePasswordDto;
import org.rina.dto.request.CredentialValidation;
import org.rina.dto.request.UserDto;
import org.rina.enums.Roles;
import org.rina.model.User;
import org.rina.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userSrv;
    private final JwtService jwtService;
    private final PasswordEncoder encodeur;
    

    /**
     * Récupérer un username.
     */
    @GetMapping
    public ResponseEntity<User> getUsername(HttpServletRequest userDto) {
        String token = userDto.getHeader(HttpHeaders.AUTHORIZATION);
        if(token==null || !token.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String utilToken = token.substring(7);
        String currentUsername = jwtService.extractUsername(utilToken);
        if(currentUsername!=null){
            User user = userSrv.findByUsername(currentUsername).orElseThrow();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Récupérer un user par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userSrv.findById(id);
        if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}

		else return ResponseEntity.notFound().build();
    }
    
    /**
     * Créer un nouveau user.
     */
    @SuppressWarnings("unused")
	@PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
    	//Vérifie que l'utilisateur n'existe pas en fonction du Username
    	Optional<User> exitingUser = userSrv.findByUsername(userDto.getUsername());
    	
    	if (exitingUser.isEmpty()) {
    		
    		User newUser = userSrv.insert(userDto.toUser(encodeur));
    		return ResponseEntity.ok().build();
        } 
        
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur existe déjà.");
    }

    /**
     * Demander un noucveau mot de passe.
     */
    @GetMapping("/{username}/newPassword")
    public ResponseEntity<User> newPassword(@PathVariable String username) {
    	// Vérifie d'abord si l'utilisateur existe en fonction du username
    	Optional<User> existingUser = userSrv.findByUsername(username);
    	
    	if (existingUser.isPresent()) {
    		User updateUser = existingUser.get();
        	//Renvoie la réponse avec le user 
            return ResponseEntity.ok(updateUser);
    	}
    
    	else return ResponseEntity.notFound().build();
    }  
    
    /**
     * Mettre à jour un mot de passe.
     */
    @GetMapping("/{username}/update")
	public ResponseEntity<String> changePWGet(@PathVariable String username, Authentication auth, Model model) {
		// vérifie si le user existe
		Optional<User> existingUser = userSrv.findByUsername(username);
		
		if ( existingUser.isPresent()) {
			User user = existingUser.get();
			// vérifie que c'est bien l'utilisateur connecté
			// Vérifie s'il a les droits admin ou prof loggé sinon déclenche exception 403
			if (auth == null || (!(auth.getName().equals(user.getUsername()) || hasRole(auth, Roles.ADMIN))))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Accés refusé");
	
			ChangePasswordDto cpd = ChangePasswordDto.createPwDto(user);
			model.addAttribute("userDto", cpd);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("l'utilisateur n'existe pas");
	}

	@PutMapping("/{username}/update")
	public ResponseEntity<String> changePWPut(@PathVariable String username,
			@Validated(CredentialValidation.class) @RequestBody ChangePasswordDto userCPwDto, Authentication auth, Model model) {
		// vérifie si le user existe
		Optional<User> existingUser = userSrv.findByUsername(username);

		if (existingUser.isPresent()) {
			User user = existingUser.get();
			// change le pw
			try {
				userSrv.changePassword(user, userCPwDto.getOldPassword(), userCPwDto.getPassword());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Mot de passe changé avec succés");
			} catch (CredentialException e) {
				return ResponseEntity.notFound().build();
			}
		}
		
		return ResponseEntity.notFound().build();
	}

    /**
     * Supprimer un user par son ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
	    if (userSrv.existsById(id)) {
	    	userSrv.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		 else return ResponseEntity.notFound().build();
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
	
	
}
