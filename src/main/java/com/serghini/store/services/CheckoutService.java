package com.serghini.store.services;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.entities.Order;
import com.serghini.store.exceptions.CartEmptyException;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@Service
@AllArgsConstructor
@Transactional
public class CheckoutService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthService authService;

    public CheckoutResponse checkout(UUID cartId) throws StripeException {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        // Create a checkout session 
        try {
    
            cartService.removeItems(cartId);
            return new CheckoutResponse(order.getId(), session.getUrl());
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            orderRepository.delete(order);
            throw e;
        }
    }
}
