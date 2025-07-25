package com.serghini.store.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serghini.store.dtos.ProductDto;
import com.serghini.store.entities.Product;
import com.serghini.store.mappers.ProductMapper;
import com.serghini.store.repositories.CategoryRepository;
import com.serghini.store.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @GetMapping("/products") 
    public List<ProductDto> getAllProducts(@RequestParam(name="categoryId", required=false) Byte categoryId, @RequestHeader(required=false, name="x-auth-token") String authToken) {
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

    @PostMapping("/products")
    public ResponseEntity<ProductDto>  createProduct(@RequestBody ProductDto request, UriComponentsBuilder uriBuilder) {
        
        System.out.println("Fuckkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk it!!!!!!!!!!!!!!");
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null)
            return ResponseEntity.badRequest().build();
        
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        var productDto = productMapper.toDto(product);
        productDto.setId(product.getId());
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto request) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null)
            return ResponseEntity.badRequest().build();

        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            return ResponseEntity.notFound().build();
        
        productMapper.updateProduct(request, product);
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            return ResponseEntity.notFound().build();  
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
