package com.serghini.store.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.serghini.store.controllers.AddItemToCartRequest;
import com.serghini.store.dtos.CartDto;
import com.serghini.store.dtos.CartItemDto;
import com.serghini.store.entities.Cart;
import com.serghini.store.entities.CartItem;
import com.serghini.store.entities.Product;
import com.serghini.store.exceptions.CartNotFoundException;
import com.serghini.store.mappers.CartMapper;
import com.serghini.store.repositories.CartRepository;
import com.serghini.store.repositories.ProductRepository;
import com.serghini.store.exceptions.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper    cartMapper;
    private final CartRepository    cartRepository;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, AddItemToCartRequest request) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();
        CartItem cartItem = cart.addItem(product, request.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        var product = productRepository.findById(productId).orElse(null);
        var cartItem = cart.getItem(productId);
        if (cartItem == null)
            throw new ProductNotFoundException();
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto  getCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        return cartMapper.toDto(cart);
    }

    public List<CartDto>  getCarts() {
        List<CartDto> carts = new LinkedList<CartDto>();

        cartRepository.findAll().forEach(cart -> {
            carts.add(cartMapper.toDto(cart));
        });
        if (carts.isEmpty())
            throw new CartNotFoundException();
        return carts;
    }

    public Void removeItems(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        cart.clear();
        cartRepository.save(cart);
        return null;
    }
}
