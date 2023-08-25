package org.rina.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * Service de déconnexion qui gère le processus de déconnexion pour les utilisateurs authentifiés.
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {


  /**
   * Gère la déconnexion de l'utilisateur. Si un en-tête d'authentification valide est trouvé,
   * le contexte de sécurité actuel est effacé.
   *
   * @param request La requête HTTP.
   * @param response La réponse HTTP.
   * @param authentication Les détails d'authentification actuels.
   */
  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    SecurityContextHolder.clearContext();
  }
}
