package com.serghini.store.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public  class OrderItemDto {
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
