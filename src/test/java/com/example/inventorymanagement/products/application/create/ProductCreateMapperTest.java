package com.example.inventorymanagement.products.application.create;

import com.example.inventorymanagement.products.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductCreateMapperTest {

    private static final String PRODUCT_NAME = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";
    private static final Double PRODUCT_PRICE = 100.0;
    private static final Integer PRODUCT_QUANTITY = 10;

    private ProductCreateMapper productCreateMapper;

    @BeforeEach
    void setUp() {
        productCreateMapper = new ProductCreateMapper();
    }

    @Test
    void testToEntity() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Product product = productCreateMapper.toEntity(command);

        assertEquals(command.getName(), product.getName());
        assertEquals(command.getDescription(), product.getDescription());
        assertEquals(command.getPrice(), product.getPrice());
        assertEquals(command.getQuantity(), product.getQuantity());
    }
}
