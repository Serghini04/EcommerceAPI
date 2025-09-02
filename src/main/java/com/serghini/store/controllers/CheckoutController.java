package com.serghini.store.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.serghini.store.dtos.CheckoutRequest;
import com.serghini.store.dtos.CheckoutResponse;
import com.serghini.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/checkout")
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request.getCartId());
    }
}
