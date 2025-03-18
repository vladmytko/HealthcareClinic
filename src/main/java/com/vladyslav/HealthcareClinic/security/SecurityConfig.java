package com.vladyslav.HealthcareClinic.security;

import com.vladyslav.HealthcareClinic.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Security configuration class for handling authentication and authorization.
 * It sets up JWT authentication and configures access control for different endpoints.
 */
@Configuration // Marks this class as a configuration class
@EnableMethodSecurity // Enables method-level security
@EnableWebSecurity // Enables Spring Security
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService; // Service to load user details from the database

    @Autowired
    private JWTAuthFilter jwtAuthFilter; // JWT filter for authentication

    // Configures security settings including authentication, authorisation, and JWT filtering
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection (JWT does not rely on session-based authentication)
                .cors(Customizer.withDefaults()) // Enables default Cross-Origin Resource Sharing (CORS) settings (useful when frontend and backend are separate)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll() // Allow public access to authentication endpoints
                        .requestMatchers("/patient/**", "/users/**", "/staff/**", "/task/**", "/image/**").authenticated()) // Require authentication for these endpoints
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build(); // Apply JWT filter before username/password authentication
    }

     // Configures authentication provider using DaoAuthenticationProvider.
     // This provider is responsible for fetching user details and verifying passwords.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService); // Use custom user details service
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return daoAuthenticationProvider;
    }

    // Password encoding using BCrypt hashing algorithm.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Authentication manager to that manages authentication requests.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
