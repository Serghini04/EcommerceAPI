package com.serghini.store.mappers;

import java.util.Date;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serghini.store.dtos.CartDto;
import com.serghini.store.dtos.CartItemDto;
import com.serghini.store.entities.Cart;
import com.serghini.store.entities.CartItem;

@Mapper(componentModel="spring")
public interface    CartMapper {
    CartDto toDto(Cart cart);
    @Mapping(target="totalPrice", expression="java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
