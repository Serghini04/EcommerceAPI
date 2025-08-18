package com.serghini.store.services;

import java.util.UUID;
import org.springframework.stereotype.Service;

import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.entities.Order;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthService authService;

    public CheckoutResponse checkout(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        cartService.removeItems(cartId);
        return new CheckoutResponse(order.getId()); 
    }
}
