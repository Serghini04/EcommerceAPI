package com.serghini.store.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.dtos.CheckoutRequest;
import com.serghini.store.repositories.CartRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CheckoutController {
    private CartRepository cartRepository;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null)
            return ResponseEntity.badRequest().body(Map.of("error", "Cart not found"));
        return ResponseEntity.noContent().build();
    }
}
