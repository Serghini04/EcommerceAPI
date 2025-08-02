package com.serghini.store.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.dtos.AuthDto;
import com.serghini.store.dtos.JwtResponse;
import com.serghini.store.dtos.UserDto;
import com.serghini.store.repositories.UserRepository;
import com.serghini.store.services.JwtService;
import com.serghini.store.mappers.UserMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    
    @PostMapping("/auth/validate")
    public boolean validate(@RequestHeader("Authorization") String authHeader) {
        var token = authHeader.substring(7);
        return jwtService.validateToken(token);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserDto> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = (String)authentication.getPrincipal();
        var user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@Validated @RequestBody AuthDto request) {
        var user = userRepository.findFirstByEmail(request.getEmail()).orElse(null);
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())){
            var token = jwtService.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
