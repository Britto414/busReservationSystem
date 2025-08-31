package com.example.busreservation.security;

import com.example.busreservation.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private Long expiration;

    // Generate token with AppUser ID as subject
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // cast to your user entity
        String userName = user.getName();

        Date expireDate = new Date(new Date().getTime() + expiration);

        return Jwts.builder()
                .setSubject(userName) // store userId instead of username
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }


    // Validate token
    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token: ");  
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }


    public String getUserName(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
