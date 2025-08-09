package com.serghini.store.controllers;

import java.util.Date;

import javax.crypto.SecretKey;

import com.serghini.store.entities.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Jwt {

    private final Claims claims;
    private final SecretKey key;

    public boolean isExpired() {
        try {
            return !claims.getExpiration().after(new Date());
        }
        catch (JwtException e) {
            return true;
        }
    }

    public Long getUserId() {
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(key).compact();
    }
}
