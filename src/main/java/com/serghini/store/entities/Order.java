package com.serghini.store.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(exclude = {"orderItems"})
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private User customer;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="total_price")
    private BigDecimal  totalPrice;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new LinkedHashSet<>();


    public static Order   fromCart(Cart cart, User customer) {
        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(order, item.getProduct(), item.getQuantity());
            order.getItems().add(orderItem);
        });
        return order;
    }
}
