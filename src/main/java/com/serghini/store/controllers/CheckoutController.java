package com.serghini.store.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.serghini.store.dtos.CheckoutRequest;
import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@RestController
@Tag(name="Checkout", description="Operations related to the checkout process")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String endpointSecret;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Process checkout for a cart", description = "Processes the checkout for the specified cart and returns the order details.")
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request.getCartId());
    }

    @PostMapping("/webhook")
    @Operation(summary = "Handle Stripe Webhook Events", description = "Handles incoming webhook events from Stripe to update order and payment statuses.")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                     @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error: " + e.getMessage());
        }

        switch (event.getType()) {
            case "payment_intent.succeeded": {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                if (paymentIntent != null) {
                    checkoutService.updatePaymentStatus(paymentIntent.getId(), "succeeded");
                }
                break;
            }
            case "checkout.session.completed": {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null) {
                    checkoutService.updateOrderStatus(session.getId(), "completed");
                }
                break;
            }
        }
        return ResponseEntity.ok("");
    }
}
