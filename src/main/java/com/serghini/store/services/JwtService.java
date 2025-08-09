package com.serghini.store.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.serghini.store.config.JwtConfig;
import com.serghini.store.controllers.Jwt;
import com.serghini.store.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    private Claims   getClaims(String token) {
        return Jwts.parser()
                            .verifyWith(jwtConfig.getSecretKey())
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
    }

    public Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                    .subject(user.getId().toString())
                    .add("email", user.getEmail())
                    .add("name", user.getName())
                    .add("role", user.getRole())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                    .build();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt generateAccessToken(User user) {
       return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

}
