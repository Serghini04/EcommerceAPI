package com.serghini.store.services;

import java.util.UUID;
import org.springframework.stereotype.Service;

import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.entities.Order;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthService authService;

    @Value("")
    private final String websiteUrl;

    public CheckoutResponse checkout(UUID cartId) throws StripeException {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        if (cart.isEmpty())
            throw new CartEmptyException();
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        // Create a checkout session 
        var builder = SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                            .setCancelUrl(websiteUrl + "/checkout-cancel");

        order.getItems().forEach(item -> {
            var lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount(item.getUnitPrice().longValue())
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(item.getProduct().getName())
                                .setDescription(item.getProduct().getDescription())
                                .build()
                        ).build()
                ).build();
            builder.addLineItem(lineItem);
        });
        
        var session = Session.create(builder.build());

        cartService.removeItems(cartId);
        return new CheckoutResponse(order.getId(), session.getUrl()); 
    }
}
