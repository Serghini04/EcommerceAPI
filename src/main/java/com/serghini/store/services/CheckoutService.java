package com.serghini.store.services;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.entities.Order;
import com.serghini.store.exceptions.CartEmptyException;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.exceptions.PaymentException;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Transactional
public class CheckoutService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;

    public void updatePaymentStatus(String paymentIntentId, String status) {
        // Find the order by paymentIntentId and update its payment status
        Order order = orderRepository.findByPaymentIntentId(paymentIntentId).orElse(null);
        if (order != null) {
            order.setPaymentStatus(status);
            orderRepository.save(order);
        }
    }

    public void updateOrderStatus(String sessionId, String status) {
        // Find the order by sessionId and update its order status
        Order order = orderRepository.findBySessionId(sessionId).orElse(null);
        if (order != null) {
            order.setOrderStatus(status);
            orderRepository.save(order);
        }
    }
    
    public CheckoutResponse checkout(UUID cartId) throws PaymentException {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.removeItems(cartId);
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException e) {
            System.out.println(e.getMessage());
            orderRepository.delete(order);
            throw e;
        }
    }
}
