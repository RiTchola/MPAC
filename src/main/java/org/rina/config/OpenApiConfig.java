package org.rina.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


/**
 * Configuration OpenAPI pour la documentation de l'API de l'application.
 * Cette classe définit les métadonnées pour la documentation générée par Swagger.
 */
@OpenAPIDefinition(
        // Informations générales sur la documentation de l'API
        info = @Info(
                // Coordonnées du contact responsable de l'API
                contact = @Contact(
                        name = "MPAC2223",
                        email = "info@gmail.com",
                        url = "https://rina.org"
                ),
                description = "Documentation OpenApi pour MPAC2223",
                title = "Spécification OpenApi - MPAC2223",
                version = "1.0",
                // Licence sous laquelle l'API est publiée
                license = @License(
                        name = "Nom de la licence",
                        url = "https://rina.org"
                ),
                termsOfService = "Conditions de service"
        ),
        // Serveurs sur lesquels l'API est déployée
        servers = {
                @Server(
                        description = "Environnement local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Environnement de PRODUCTION",
                        url = "https://api.rina.org"
                )
        },
        // Exigences de sécurité pour accéder à l'API
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        // Définition du schéma de sécurité utilisé par l'API
        name = "bearerAuth",
        description = "Description de l'authentification JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        // Indique que le token sera inclus dans le header de la requête
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    // Cette classe n'a pas besoin d'avoir un corps puisque toutes ses configurations sont définies via des annotations.
}