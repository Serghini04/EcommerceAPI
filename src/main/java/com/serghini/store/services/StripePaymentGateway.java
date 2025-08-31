package com.serghini.store.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

import com.serghini.store.entities.Order;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

public class StripePaymentGateway implements PaymentGateway {
    
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
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
                        .setUnitAmount(item.getUnitPrice().multiply(BigDecimal.valueOf(100)).longValue())
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
        return null;
    }
}
