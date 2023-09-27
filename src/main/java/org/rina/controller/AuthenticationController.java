package org.rina.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rina.dto.response.AuthenticationResponse;
import org.rina.service.AuthenticationService;
import org.rina.dto.request.AuthenticationRequest;
import org.rina.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    /**
     * Endpoint pour l'enregistrement d'un nouvel utilisateur.
     *
     * @param request Les informations d'enregistrement de l'utilisateur.
     * @return Une réponse HTTP contenant le résultat de l'enregistrement.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint pour l'authentification d'un utilisateur.
     *
     * @param request Les informations d'authentification de l'utilisateur.
     * @return Une réponse HTTP contenant le résultat de l'authentification.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /**
     * Endpoint pour rafraîchir le jeton d'authentification.
     *
     * @param request  La requête HTTP.
     * @param response La réponse HTTP dans laquelle le nouveau jeton sera renvoyé.
     * @throws IOException En cas d'erreur lors de la gestion de la réponse HTTP.
     */
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}