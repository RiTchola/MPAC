package org.rina.config;

import org.rina.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration principale de l'application. Cette classe fournit des beans nécessaires
 * pour la sécurité et l'authentification des utilisateurs.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  // Référence au dépôt pour les opérations liées aux utilisateurs.
  private final UserRepository repository;

  /**
   * Fournit un service personnalisé pour récupérer les détails de l'utilisateur à partir de la base de données.
   *
   * @return Le service de détails d'utilisateur.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Fournit un fournisseur d'authentification pour l'authentification des utilisateurs basée sur le DAO.
   *
   * @return Le fournisseur d'authentification.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  /**
   * Fournit un gestionnaire d'authentification pour traiter l'authentification des utilisateurs.
   *
   * @param config La configuration d'authentification.
   * @return Le gestionnaire d'authentification.
   * @throws Exception si une erreur se produit lors de la récupération du gestionnaire d'authentification.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Fournit un encodeur de mot de passe pour le hachage sécurisé des mots de passe.
   *
   * @return L'encodeur de mot de passe.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
