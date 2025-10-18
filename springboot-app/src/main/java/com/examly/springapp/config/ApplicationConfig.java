package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

@Configuration
public class ApplicationConfig {

    private final UserRepo userRepo;

    // Constructor injection for UserRepo
    public ApplicationConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Bean definition for UserDetailsService.
     * This method returns an anonymous implementation of UserDetailsService,
     * which loads a user by username and throws UsernameNotFoundException if the
     * user is not found.
     * 
     * @return an implementation of UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                return new UserPrinciple(user);
            }
        };
    }

    /**
     * Bean definition for PasswordEncoder.
     * This method returns a BCryptPasswordEncoder, which is used to encode
     * passwords.
     * 
     * @return a PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for AuthenticationProvider.
     * This method returns a DaoAuthenticationProvider, which uses the
     * UserDetailsService and PasswordEncoder
     * to authenticate users.
     * 
     * @return an AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean definition for AuthenticationManager.
     * This method returns the AuthenticationManager from the
     * AuthenticationConfiguration.
     * 
     * @param authenticationConfiguration the AuthenticationConfiguration
     * @return an AuthenticationManager
     * @throws Exception if an error occurs while getting the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
