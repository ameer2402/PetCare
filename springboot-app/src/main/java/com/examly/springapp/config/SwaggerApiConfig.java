package com.examly.springapp.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerApiConfig {

    /**
     * Configures the OpenAPI for Swagger documentation.
     *
     * @return An OpenAPI object containing the Swagger configuration.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Care")
                        .description(
                                "PetCare lets pet owners schedule veterinary appointments, while administrators can manage and approve bookings, and track treatments for a smooth pet healthcare experience")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Team 3")
                                .email("petcare.help@yopmail.com")
                                .url("https://example.com")) // after completing our URL
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(new Server().url(
                        "https://8080-fadffcfdbddbdfbdfacfcddfbedbebb.premiumproject.examly.io/")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(HTTP)
                                        .scheme("bearer")
                                        .description("Provide the JWT token.")
                                        .bearerFormat("JWT")));
    }
}