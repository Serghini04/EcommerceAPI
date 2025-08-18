package com.serghini.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serghini.store.entities.Order;
import com.serghini.store.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User customer); 
}
