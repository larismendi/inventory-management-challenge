package com.example.inventorymanagement.products.infrastructure.mapper;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;

public class ProductMapper {
    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
