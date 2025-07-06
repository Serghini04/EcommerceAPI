package com.serghini.store.repositories;

import java.util.List;

import com.serghini.store.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product>   findByCategoryId(Byte categoryId);
}