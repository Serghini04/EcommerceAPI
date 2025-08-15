package com.serghini.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.serghini.store.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
