package com.serghini.store.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.serghini.store.entities.CartItem;

import lombok.Data;

@Data
public class CartDto {
    private UUID    id;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
