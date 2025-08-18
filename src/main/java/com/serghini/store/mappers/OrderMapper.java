package com.serghini.store.mappers;

import org.mapstruct.Mapper;

import com.serghini.store.dtos.OrderDto;
import com.serghini.store.entities.Order;

@Mapper(componentModel="spring")
public interface OrderMapper {
    OrderDto toDto(Order order);   
}
