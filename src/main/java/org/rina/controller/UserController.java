package org.rina.controller;

import org.rina.config.JwtService;
import org.rina.dto.request.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rina.model.User;
import org.rina.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

//    @GetMapping
//    public ResponseEntity<List<User>> getAll() {
//        List<User> users = userService.findAll();
//        return ResponseEntity.ok(users);
//    }
    
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
    	// Vérifie qu'il n'existe pas déjà cet username
    	Optional<User> exitingUser = userService.findByUsername(userDto.getUsername());
    	if (exitingUser.isEmpty()) {
    		
    		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    		User newUser = userService.insert(userDto.toUser());
    		return ResponseEntity.ok().build();
        } 
        
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur existe déjà.");
    }
//    	User user = User.builder()
//                .username(userDto.getUsername())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .role(userDto.getRole())
//                .build();
//        User savedUser = userService.save(user);
//
//        return ResponseEntity.ok(savedUser);
    

    @PutMapping("/{username}")
    public ResponseEntity<User> newPassword(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
    	// Vérifie d'abord si l'utilisateur existe en fonction du username
    	Optional<User> existingUser = userService.findByUsername(username);
    	
    	if (existingUser.isPresent()) {
    		
    		// Mise à jour l'utilisateur existant avec les nouvelles valeurs
        	userDto.setUsername(username);
        	userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        	User updateUser = userService.updateUser(username, userDto.toUser());
            return ResponseEntity.ok().build();
    	}
    
    	else return ResponseEntity.notFound().build();
    }       
    //userUpdate.setUsername(userDto.getUsername());
     //userUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
     // userUpdate.setRole(userDto.getRole());
        //User savedUser = userService.save(userDto.toUser());
        //return ResponseEntity.ok(savedUser);
   

    @GetMapping
    public ResponseEntity<User> getUsername(HttpServletRequest userDto) {
        String token = userDto.getHeader(HttpHeaders.AUTHORIZATION);
        if(token==null || !token.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String utilToken = token.substring(7);
        String currentUsername = jwtService.extractUsername(utilToken);
        if(currentUsername!=null){
            User user = userService.findByUsername(currentUsername).orElseThrow();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return ResponseEntity.of(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
	    if (userService.existsById(id)) {
	    	userService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		 else return ResponseEntity.notFound().build();
	}
}
