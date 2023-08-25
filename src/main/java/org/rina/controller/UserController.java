package org.rina.controller;

import org.rina.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
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
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody User request) {
        User userUpdate = userService.findByUsername(username).get();
        userUpdate.setUsername(request.getUsername());
        userUpdate.setPassword(passwordEncoder.encode(request.getPassword()));
        userUpdate.setRole(request.getRole());
        User savedUser = userService.save(userUpdate);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/username")
    public ResponseEntity<User> getByUsername(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
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
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return ResponseEntity.of(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
