package com.serghini.store.controllers;

import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serghini.store.dtos.ProductDto;
import com.serghini.store.dtos.UserDto;
import com.serghini.store.entities.Product;
import com.serghini.store.mappers.ProductMapper;
import com.serghini.store.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository; 
    private final ProductMapper productMapper;

    @GetMapping("/products") 
    public List<ProductDto> getAllProducts(@RequestParam(name="categoryId", required=false, defaultValue="") Byte categoryId, @RequestHeader(required=false, name="x-auth-token") String authToken) {
        List<Product> products;
        System.out.print(authToken);
        if (categoryId != null)
            products = productRepository.findByCategoryId(categoryId);
        else
            products = productRepository.findAll();
        return products.stream()
            .map(productMapper::toDto)
            .toList();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto>  getProductbyId(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping("/users")
    public  UserDto createUser(@RequestBody UserDto data) {
         return data;
    }
}
