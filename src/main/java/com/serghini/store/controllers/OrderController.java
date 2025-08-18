package com.serghini.store.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.dtos.OrderDto;
import com.serghini.store.entities.User;
import com.serghini.store.mappers.OrderMapper;
import com.serghini.store.repositories.OrderRepository;
import com.serghini.store.services.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class OrderController {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @GetMapping("/orders")
    @Transactional(readOnly = true)
    public List<OrderDto>   getAllOrders() {
        User user = authService.getCurrentUser();
        return orderRepository.findAllByCustomer(user)
        .stream().map(orderMapper::toDto)
        .toList();
    }
}
