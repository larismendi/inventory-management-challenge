package com.example.inventorymanagement.products.infrastructure.outbound.database.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaAuditing
public class ProductEntityTest {

    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final Double PRODUCT_PRICE = 99.99;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final String PRODUCT_NAME_NEW = "Updated Name";

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenProductEntityWhenPersistedThenCreatedAtAndUpdatedAtAreSet() {
        ProductEntity product = ProductEntity.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        ProductEntity persistedProduct = entityManager.persistAndFlush(product);

        assertNotNull(persistedProduct.getCreatedAt());
        assertNotNull(persistedProduct.getUpdatedAt());
        assertEquals(persistedProduct.getCreatedAt(), persistedProduct.getUpdatedAt());
    }

    @Test
    void givenProductEntityWhenUpdatedThenUpdatedAtIsChanged() throws InterruptedException {
        ProductEntity product = ProductEntity.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        ProductEntity persistedProduct = entityManager.persistAndFlush(product);
        LocalDateTime initialUpdatedAt = persistedProduct.getUpdatedAt();

        Thread.sleep(1000);

        persistedProduct = persistedProduct.toBuilder()
                .name(PRODUCT_NAME_NEW)
                .build();
        ProductEntity updatedProduct = entityManager.merge(persistedProduct);
        entityManager.flush();

        assertNotNull(updatedProduct.getUpdatedAt());
        assertTrue(updatedProduct.getUpdatedAt().isAfter(initialUpdatedAt));
    }
}
