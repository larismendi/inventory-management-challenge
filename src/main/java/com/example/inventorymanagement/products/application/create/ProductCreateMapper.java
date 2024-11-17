package com.example.inventorymanagement.products.application.create;

import com.example.inventorymanagement.products.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateMapper {

    public Product toEntity(ProductCreateCommand command) {
        return Product.builder()
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .quantity(command.getQuantity())
                .build();
    }

}
