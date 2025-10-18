package com.examly.springapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Ameer Khan
 *        Configuration class to handle Cross-Origin Resource Sharing (CORS)
 *        settings.
 * 
 *        Annotated with `@Configuration` to denote it as a Spring
 *        configuration class.
 *        Uses `@EnableWebMvc` to enable Spring MVC features.
 *        Implements `WebMvcConfigurer` to customize web configurations like
 *        CORS.
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry the `CorsRegistry` object used to configure CORS settings.
     *                 - Adds a global CORS mapping (`/**`) to allow requests from
     *                 any origin.
     *                 - Specifies allowed HTTP methods: GET, POST, PUT, OPTIONS,
     *                 DELETE.
     *                 - Permits all headers in the request.
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                // .allowedOrigins("https://8081-bdaddddefbdbdfbdfacfcddfbedbebb.premiumproject.examly.io/")
                .allowedOriginPatterns("https://*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Origin")
                .allowCredentials(true);
    }
}
