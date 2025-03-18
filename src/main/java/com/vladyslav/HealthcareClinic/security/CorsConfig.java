package com.vladyslav.HealthcareClinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // This class is used to define settings that will be applied to the application
public class CorsConfig {

    @Bean // Automatically applies this method when app starts
    // Cors Registry allows defining which origins (frontend URLs) can communicate with backend.
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // All endpoints in the backend can be accessed by the frontend
                        .allowedOrigins("http://localhost:3000") // Allows frontend only from this URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Define which HTTP methods allows for CORS requests.
                        .allowedHeaders("*") // Allows the front end to send any type of headers (e.g., Authorisation)
                        .allowCredentials(true); // Allows sending cookies and Authenticate requests using Authorisation
            }
        };
    }
}
