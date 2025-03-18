package com.vladyslav.HealthcareClinic.security;

import com.vladyslav.HealthcareClinic.service.CustomUserDetailService;
import com.vladyslav.HealthcareClinic.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWTAuthFilter is a security filter that intercepts incoming HTTP requests,
 * extracts the JWT token, validates it, and sets up Spring Security authentication.
 */

@Component // Marks this class as a Spring-managed component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils; // Utility class for handling JWT operations

    @Autowired
    private CustomUserDetailService customUserDetailService; // Custom service to load user details

    /**
     * This method intercepts requests, extracts the JWT token, and validates authentication.
     * It runs once per request to check if the user is authenticated.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extracts the Authorization header from the HTTP request
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // If no Authorization header is present or it's empty, continue the filter chain
        if(authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header (removing "Bearer " prefix)
        jwtToken = authHeader.substring(7);

        // Extract the username (email) from the JWT token
        userEmail = jwtUtils.extractUsername(jwtToken);

        // If the extracted email is not null and user is not already authenticated
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details (e.g., username, password, roles) from the database
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);

            // Validate the JWT token with the user details
            if(jwtUtils.isValidToken(jwtToken, userDetails)) {

                // Create a new SecurityContext for authentication
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Create an authentication token with user details and roles
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication details from the request
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the SecurityContext
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Continue processing the request in the filter chain
        filterChain.doFilter(request,response);
    }
}
