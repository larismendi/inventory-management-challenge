package com.example.inventorymanagement.products.application.update;

import com.example.inventorymanagement.products.domain.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductUpdateMapper {

    public Product updateExistingProduct(Product existingProduct, ProductUpdateCommand command) {
        return Product.builder()
                .id(existingProduct.getId())
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .quantity(command.getQuantity())
                .createdAt(existingProduct.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
