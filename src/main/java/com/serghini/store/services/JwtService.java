package com.serghini.store.services;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    public String generateToken(String email) {
        final long EXPIRATION_TIME = 86400 * 1000; // 1 day in milliseconds
        return Jwts.builder()
                   .subject(email)
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(Keys.hmacShaKeyFor("secret".getBytes())).compact();
    }
}
