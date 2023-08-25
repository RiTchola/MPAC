package org.rina.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtre d'authentification JWT qui extrait le token JWT du header "Authorization"
 * d'une requête entrante et valide ce token.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // Service pour les opérations liées au JWT.
  private final JwtService jwtService;

  // Service pour récupérer les détails de l'utilisateur.
  private final UserDetailsService userDetailsService;

  /**
   * Effectue une filtration pour chaque requête entrante pour s'assurer qu'elle contient un JWT valide.
   *
   * @param request La requête HTTP entrante.
   * @param response La réponse HTTP sortante.
   * @param filterChain Chaîne de filtres à laquelle ce filtre appartient.
   * @throws ServletException si une erreur du servlet se produit.
   * @throws IOException si une erreur d'entrée/sortie se produit.
   */
  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    // Si la requête concerne l'authentification, passez-la sans filtrage.
    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Récupérer le header "Authorization" de la requête.
    final String authHeader = request.getHeader("Authorization");

    // Initialisation des variables pour le token JWT et l'email de l'utilisateur.
    final String jwt;
    final String userEmail;

    // Si le header est absent ou ne commence pas par "Bearer ", passer la requête sans filtrage.
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Extraire le token JWT du header "Authorization".
    jwt = authHeader.substring(7);

    // Extraire l'email de l'utilisateur à partir du token JWT.
    userEmail = jwtService.extractUsername(jwt);

    // Si l'email n'est pas nul et qu'aucune authentification n'est présente dans le contexte de sécurité,
    // tentez d'authentifier l'utilisateur.
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      // Si le token JWT est valide, authentifiez l'utilisateur.
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        // Associer les détails d'authentification à l'objet token.
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // Définir l'authentification pour le contexte de sécurité actuel.
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // Passer la requête à la prochaine étape de la chaîne de filtres.
    filterChain.doFilter(request, response);
  }
}