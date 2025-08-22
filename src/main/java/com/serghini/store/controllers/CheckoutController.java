package com.serghini.store.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.dtos.CheckoutRequest;
import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.entities.Order;
import com.serghini.store.entities.OrderItem;
import com.serghini.store.entities.OrderStatus;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.OrderRepository;
import com.serghini.store.services.AuthService;
import com.serghini.store.services.CartService;
import com.serghini.store.services.CheckoutService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        var response = checkoutService.checkout(request.getCartId());
        return ResponseEntity.ok(response);
    }
}
