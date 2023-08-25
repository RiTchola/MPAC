package org.rina.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Web pour l'application, notamment pour définir les paramètres CORS.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // URL du client qui consommera l'API. Utilisée pour définir les paramètres CORS.
    @Value("${app.client.url}")
    private String clientURL;

    /**
     * Configure les paramètres CORS pour l'application.
     * Cela permettra au client de consommer l'API en toute sécurité en évitant les problèmes de Cross-Origin.
     *
     * @param registry Le registre CORS auquel ajouter les configurations.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Autorise les requêtes provenant de l'URL client sur tous les endpoints (/**).
        // Toutes les méthodes et en-têtes sont autorisées, et les cookies/headers d'authentification
        // peuvent être envoyés avec les requêtes.
        registry.addMapping("/**")
                .allowedOrigins(clientURL)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
