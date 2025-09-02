package com.serghini.store.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.serghini.store.entities.Order;
import com.serghini.store.entities.OrderItem;
import com.serghini.store.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;


@Service
public class StripePaymentGateway implements PaymentGateway {
    
    @Value("${websiteUrl}")
    private String websiteUrl;

    private ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(item.getProduct().getName())
                                    .setDescription(item.getProduct().getDescription())
                                    .build();
    }

    private PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(item.getUnitPrice().multiply(BigDecimal.valueOf(100)).longValue())
                            .setProductData(createProductData(item)).build();
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                    .setQuantity(Long.valueOf(item.getQuantity()))
                    .setPriceData(createPriceData(item))
                    .build();
    }

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                                    .setMode(SessionCreateParams.Mode.PAYMENT)
                                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                                    .setCancelUrl(websiteUrl + "/checkout-cancel");
            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });
            
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }
}
