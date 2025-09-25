package com.serghini.store.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.serghini.store.exceptions.*;
import com.serghini.store.dtos.OrderDto;
import com.serghini.store.entities.User;
import com.serghini.store.mappers.OrderMapper;
import com.serghini.store.repositories.OrderRepository;
import com.serghini.store.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@AllArgsConstructor
@Tag(name="Orders", description="Operations related to customer orders")
public class OrderController {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @GetMapping("/orders")
    @Operation(summary="Retrieves a list of all orders for the authenticated user.")
    public List<OrderDto>   getAllOrders() {
        User user = authService.getCurrentUser();
        return orderRepository.findAllByCustomer(user)
        .stream().map(orderMapper::toDto)
        .toList();
    }

    @GetMapping("/orders/{orderId}")
    @Operation(summary="Retrieves the details of a specific order by its ID.")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user))
            throw new AccessDeniedException("You don't have access to this order");
        return ResponseEntity.ok(orderMapper.toDto(order));
    }
}
