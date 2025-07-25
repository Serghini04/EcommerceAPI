package com.serghini.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.serghini.store.dtos.ProductDto;
import com.serghini.store.entities.Product;

@Mapper(componentModel = "spring")
public interface  ProductMapper {
    @Mapping(source = "category.id", target="categoryId")
    ProductDto  toDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto dto);

    @Mapping(target = "category", ignore = true)
    void updateProduct(ProductDto productDto, @MappingTarget Product product);
}