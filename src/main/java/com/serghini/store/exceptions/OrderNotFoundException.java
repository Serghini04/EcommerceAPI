package com.serghini.store.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Order not found");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
};