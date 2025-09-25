package com.serghini.store.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.serghini.store.dtos.CheckoutRequest;
import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@AllArgsConstructor
@Tag(name="Checkout", description="Operations related to the checkout process")
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    @Operation(summary = "Process checkout for a cart", description = "Processes the checkout for the specified cart and returns the order details.")
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request.getCartId());
    }
}
