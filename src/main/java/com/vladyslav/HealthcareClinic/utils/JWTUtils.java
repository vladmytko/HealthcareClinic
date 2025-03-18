package com.vladyslav.HealthcareClinic.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for generating, validating, and extracting information from JWT tokens.
 */
@Service // Marks this class as a Spring service component
public class JWTUtils {

    // Expiration time for JWT tokens (7 days)
    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 7; //for 7 days

    // Secret key used for signing the JWT
    private final SecretKey Key;

    /**
     * Constructor initialises the secret key for signing JWT tokens.
     */
    public JWTUtils() {
        // Secret key string
        // Problem: It should be stored securely in environment variables instead of being hardcoded.
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

        // Decode the secret key string into bytes
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));

        // Generate the HMAC SHA-256 secret key. This key will be used for both signing and verifying tokens.
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");

    }


    // Generates a JWT token for the authenticated user.
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Set the subject as the username
                .issuedAt(new Date(System.currentTimeMillis()))  // Set the issue date
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(Key) // Sign the token with the secret key
                .compact(); // Build and return the JWT token
    }

    //Extracts the username from a given JWT token.
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Extracts a specific claim from the JWT token.
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    // Validates the JWT token by checking the username and expiration.
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Checks if the JWT token has expired.
    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
