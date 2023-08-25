package org.rina.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

// Annoter la classe comme une configuration.
@Configuration
// Activer la sécurité Web avec Spring Security.
@EnableWebSecurity
// Générer un constructeur avec tous les champs finals comme arguments grâce à Lombok.
@RequiredArgsConstructor
// Activer la sécurité au niveau de la méthode.
@EnableMethodSecurity
public class SecurityConfiguration {

  // Injection des dépendances.
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  // Définition du filtre de sécurité.
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // Désactiver CSRF (Cross-Site Request Forgery).
            .csrf()
            .disable()
            // Activer la configuration CORS (Cross-Origin Resource Sharing).
            .cors().and()
            // Définir les autorisations d'accès aux routes.
            .authorizeHttpRequests()
            // Autoriser l'accès sans authentification à certaines routes, principalement liées à Swagger.
            .requestMatchers(
                    "/api/v1/auth/**",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
            )
            .permitAll()
            // Toutes les autres demandes nécessitent une authentification.
            .anyRequest()
            .authenticated()
            .and()
            // Gestion de la session - la politique est définie pour qu'aucune session ne soit créée.
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Fournir un fournisseur d'authentification personnalisé.
            .authenticationProvider(authenticationProvider)
            // Ajouter un filtre JWT avant le filtre d'authentification standard de Spring Security.
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            // Configurer la déconnexion.
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            // Ajouter un gestionnaire de déconnexion personnalisé.
            .addLogoutHandler(logoutHandler)
            // Nettoyer le contexte de sécurité après la déconnexion réussie.
            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    // Construire et renvoyer la configuration de sécurité.
    return http.build();
  }
}
