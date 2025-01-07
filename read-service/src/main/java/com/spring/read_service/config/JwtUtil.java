package com.spring.read_service.config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class JwtUtil {
    private static final String SECRET = "mysecret12345";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    // Generate a JWT token with a username (subject)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Validate the token with the username
    public boolean validateToken(String token, String username) {
        String tokenUser = extractUsername(token);
        return tokenUser.equals(username) && !isTokenExpired(token);
    }

    // Extract the username (subject) from the JWT
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // Remove the 'Bearer ' prefix if present and parse the JWT
                .getBody() // Get the claims from the token
                .getSubject(); // Extract the username (subject)
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token); // Extract expiration date from the token
        return expiration.before(new Date()); // Check if the token is expired
    }

    // Extract the expiration date from the token
    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET) // Set the secret key to verify the JWT
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // Remove the 'Bearer ' prefix if present and parse the JWT
                .getBody() // Get the claims from the token
                .getExpiration(); // Extract the expiration date
    }
}