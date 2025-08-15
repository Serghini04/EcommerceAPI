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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CheckoutController {
    private CartRepository cartRepository;
    private CartService cartService;
    private AuthService authService;
    private OrderRepository orderRepository;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        var cart = cartRepository.findById(request.getCartId()).orElse(null);
        if (cart == null)
            return ResponseEntity.badRequest().body(Map.of("error", "Cart not found"));
        if (cart.getItems().isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty!"));
        var order = cart.fromCart();
        orderRepository.save(order);
        cartService.removeItems(request.getCartId());
        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
