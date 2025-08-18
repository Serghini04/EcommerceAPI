package com.serghini.store.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serghini.store.dtos.CartDto;
import com.serghini.store.dtos.CartItemDto;
import com.serghini.store.dtos.UpdateCartItemRequest;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.exceptions.ProductNotFoundException;
import com.serghini.store.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name="Cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/carts")
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        CartDto cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    
    @PostMapping("/carts/{cartId}/items")
    @Operation(summary="Adds a product to the cart.")
    public ResponseEntity<?>  addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.ok(cartItemDto);
    }

    @PutMapping("/carts/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(@PathVariable UUID cartId, @PathVariable Long productId, @Valid @RequestBody UpdateCartItemRequest request) {
        CartItemDto item = cartService.updateItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(item);
    }
    

    @GetMapping("/carts")
    public ResponseEntity<List<CartDto>> getCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/carts/{cartId}/items")
    public ResponseEntity<Void> removeItem(@PathVariable UUID cartId) {
        cartService.removeItems(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart."));
    }
}
