package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 *
 * @author Mohammed Ameer Khan
 *         Annotated with `@Configuration` to indicate a Spring configuration
 *         class.
 *         `@EnableWebSecurity` to enable web security features.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor injection for AuthenticationProvider and JwtAuthenticationFilter
    public SecurityConfig(AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the Spring Security filter chain.
     *
     * @param httpSecurity the `HttpSecurity` object used to customize security
     *                     settings.
     *                     - Disables CSRF and CORS protections.
     *                     - Configures public access to specific API endpoints
     *                     (`permitAll`).
     *                     - Ensures authentication is required for all other
     *                     endpoints.
     * @return the configured `SecurityFilterChain` object.
     * @throws Exception if there is an issue configuring the security chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors()
                .and()
                .csrf(c -> c.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/register",
                                "/api/login",
                                "/swagger-ui/**", // Permit access to Swagger UI
                                "/v3/api-docs/**", // Permit access to API docs
                                "/swagger-resources/**", // Permit access to Swagger resources
                                "/webjars/**")
                        .permitAll()
                        .requestMatchers("/api/pet/**").hasRole("PETOWNER") // Pet POST operation
                        .requestMatchers(HttpMethod.POST, "/api/appointments").hasRole("PETOWNER") // Appointment POST
                                                                                                   // operation
                        .requestMatchers(HttpMethod.GET, "/api/appointments/user/{userId}").hasRole("PETOWNER") // Appointment
                                                                                                                // GET
                                                                                                                // operation
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/{appointmentId}")
                        .hasAnyRole("PETOWNER", "ADMIN") // Appointment PUT operation
                        .requestMatchers(HttpMethod.GET, "/api/appointments/{appointmentId}").hasRole("PETOWNER") // Appointment
                                                                                                                  // GET
                                                                                                                  // operation
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/{appointmentId}").hasRole("PETOWNER") // Appointment
                                                                                                                     // DELETE
                                                                                                                     // operation
                        .requestMatchers(HttpMethod.GET, "/api/appointments").hasRole("ADMIN") // Appointment GET
                                                                                               // operation for ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/feedback").hasRole("ADMIN") // Feedback GET operation
                        .requestMatchers(HttpMethod.POST, "/api/feedback").hasRole("PETOWNER") // Feedback POST
                                                                                               // operation
                        .requestMatchers(HttpMethod.GET, "/api/feedback/user/{userId}").hasRole("PETOWNER") // Feedback
                                                                                                            // GET
                                                                                                            // operation
                        .requestMatchers(HttpMethod.DELETE, "/api/feedback/{feedbackId}").hasRole("PETOWNER") // Feedback
                                                                                                              // DELETE
                                                                                                              // operation
                        .requestMatchers(HttpMethod.POST, "/api/appointments/{appointmentId}/status").hasRole("ADMIN") // Feedback
                                                                                                                       // DELETE
                                                                                                                       // operation
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

// package com.examly.springapp.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import
// org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// /**
// * Security configuration for the application.
// *
// * @author Mohammed Ameer Khan
// * Annotated with `@Configuration` to indicate a Spring configuration
// * class.
// * `@EnableWebSecurity` to enable web security features.
// */
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// private final AuthenticationProvider authenticationProvider;
// private final JwtAuthenticationFilter jwtAuthenticationFilter;

// // Constructor injection for AuthenticationProvider and
// JwtAuthenticationFilter
// public SecurityConfig(AuthenticationProvider authenticationProvider,
// JwtAuthenticationFilter jwtAuthenticationFilter) {
// this.authenticationProvider = authenticationProvider;
// this.jwtAuthenticationFilter = jwtAuthenticationFilter;
// }

// /**
// * Configures the Spring Security filter chain.
// *
// * @param httpSecurity the `HttpSecurity` object used to customize security
// * settings.
// * - Disables CSRF and CORS protections.
// * - Configures public access to specific API endpoints
// * (`permitAll`).
// * - Ensures authentication is required for all other
// * endpoints.
// * @return the configured `SecurityFilterChain` object.
// * @throws Exception if there is an issue configuring the security chain.
// */
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
// throws Exception {
// return httpSecurity
// .cors()
// .and()
// .csrf(c -> c.disable())
// .authorizeHttpRequests(authorize -> authorize
// .requestMatchers(
// "/api/register",
// "/api/login",
// // "/api/feedback/**",
// // "/api/appointments/**",
// // "/api/pet/**",
// // "/api/users/**",
// // "/api/user/**",
// "/swagger-ui/**", // Permit access to Swagger UI
// "/v3/api-docs/**", // Permit access to API docs
// "/swagger-resources/**", // Permit access to Swagger resources
// "/webjars/**")
// .permitAll()
// .anyRequest().authenticated())
// .sessionManagement(session -> session
// .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
// .authenticationProvider(authenticationProvider)
// .addFilterBefore(jwtAuthenticationFilter,
// UsernamePasswordAuthenticationFilter.class)
// .build();
// }

// }
