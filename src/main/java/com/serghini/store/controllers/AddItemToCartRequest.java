package com.serghini.store.controllers;

import lombok.Data;

@Data
public class AddItemToCartRequest {
    private Long productId;
    private Long quantity;
}
