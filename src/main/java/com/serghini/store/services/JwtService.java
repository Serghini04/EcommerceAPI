package com.serghini.store.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.serghini.store.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    private long tokenExpiration = 86400 * 1000; // millisec => 1 day
    
    public String generateToken(User user) {
        return Jwts.builder()
            .subject(user.getId().toString())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .claim("email", user.getEmail())
            .claim("name", user.getName())
            .compact();
    }

    public Claims   getClaims(String token) {
        return Jwts.parser()
                            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e) {
            return false;
        }
    }

    public String getIdFormToken(String token) {
        return getClaims(token).getSubject();
    }
}
