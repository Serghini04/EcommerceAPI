package com.serghini.store.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.serghini.store.entities.User;
import com.serghini.store.repositories.UserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthService {
    public UserRepository userRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = (String) authentication.getPrincipal();
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
