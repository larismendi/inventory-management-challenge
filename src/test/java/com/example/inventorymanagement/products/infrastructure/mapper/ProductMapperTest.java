package com.example.inventorymanagement.products.infrastructure.mapper;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final Double PRODUCT_PRICE = 99.99;
    private static final Integer PRODUCT_QUANTITY = 10;

    @Test
    void testToDomainShouldMapEntityToDomainCorrectly() {
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2023, 1, 2, 10, 0);
        ProductEntity entity = ProductEntity.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        Product product = ProductMapper.toDomain(entity);

        assertNotNull(product);
        assertEquals(entity.getId(), product.getId());
        assertEquals(entity.getName(), product.getName());
        assertEquals(entity.getDescription(), product.getDescription());
        assertEquals(entity.getPrice(), product.getPrice());
        assertEquals(entity.getQuantity(), product.getQuantity());
        assertEquals(entity.getCreatedAt(), product.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), product.getUpdatedAt());
    }

    @Test
    void testToEntityShouldMapDomainToEntityCorrectly() {
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2023, 1, 2, 10, 0);
        Product product = Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        ProductEntity entity = ProductMapper.toEntity(product);

        assertNotNull(entity);
        assertEquals(product.getId(), entity.getId());
        assertEquals(product.getName(), entity.getName());
        assertEquals(product.getDescription(), entity.getDescription());
        assertEquals(product.getPrice(), entity.getPrice());
        assertEquals(product.getQuantity(), entity.getQuantity());
        assertEquals(product.getCreatedAt(), entity.getCreatedAt());
        assertEquals(product.getUpdatedAt(), entity.getUpdatedAt());
    }
}
