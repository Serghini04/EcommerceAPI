package com.serghini.store.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.config.JwtConfig;
import com.serghini.store.dtos.AuthDto;
import com.serghini.store.dtos.JwtResponse;
import com.serghini.store.dtos.UserDto;
import com.serghini.store.repositories.UserRepository;
import com.serghini.store.services.JwtService;
import com.serghini.store.mappers.UserMapper;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @GetMapping("/auth/me")
    public ResponseEntity<UserDto> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = (String) authentication.getPrincipal();
        var user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody AuthDto request, HttpServletResponse response) {
        var user = userRepository.findFirstByEmail(request.getEmail()).orElse(null);
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())){
            var accessToken = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            var cookie = new Cookie("refreshToken", refreshToken.toString());
            cookie.setHttpOnly(true);
            cookie.setPath("/auth/refresh");
            cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
            cookie.setSecure(true);
            response.addCookie(cookie);
            return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value="refreshToken") String refreshToken) { 
        
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }
}
