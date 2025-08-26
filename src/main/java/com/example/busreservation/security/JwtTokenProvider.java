package com.example.busreservation.security;


import com.example.busreservation.entities.AppUser;
import com.example.busreservation.models.ReservationApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
        AppUser user = (AppUser) authentication.getPrincipal(); // cast to your user entity
        String userId = String.valueOf(user.getId());

        Date expireDate = new Date(new Date().getTime() + expiration);

        return Jwts.builder()
                .setSubject(userId) // store userId instead of username
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Invalid Token");
        } catch (ExpiredJwtException e) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Token Expired");
        } catch (UnsupportedJwtException e) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Unsupported Token");
        } catch (IllegalArgumentException e) {
            throw new ReservationApiException(HttpStatus.BAD_REQUEST, "Invalid Argument");
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
