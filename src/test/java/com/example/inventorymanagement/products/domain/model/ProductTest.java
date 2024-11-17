package com.example.inventorymanagement.products.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private static final Long PRODUCT_ID_1 = 1L;
    private static final String PRODUCT_NAME_1 = "Product 1";
    private static final String PRODUCT_DESCRIPTION_1 = "Description of product 1";
    private static final Double PRODUCT_PRICE_1 = 200.0;
    private static final Integer PRODUCT_QUANTITY_1 = 10;
    private static final Long PRODUCT_ID_2 = 2L;
    private static final String PRODUCT_NAME_2 = "Product 2";
    private static final String PRODUCT_DESCRIPTION_2 = "Description of product 2";
    private static final Double PRODUCT_PRICE_2 = 150.0;
    private static final Integer PRODUCT_QUANTITY_2 = 16;
    private static final Long PRODUCT_ID_EQUALS = 3L;

    @Test
    public void testProductBuilder() {
        Product product = Product.builder()
                .id(PRODUCT_ID_1)
                .name(PRODUCT_NAME_1)
                .description(PRODUCT_DESCRIPTION_1)
                .price(PRODUCT_PRICE_1)
                .quantity(PRODUCT_QUANTITY_1)
                .build();

        assertThat(product.getId()).isEqualTo(PRODUCT_ID_1);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_1);
        assertThat(product.getDescription()).isEqualTo(PRODUCT_DESCRIPTION_1);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_1);
        assertThat(product.getQuantity()).isEqualTo(PRODUCT_QUANTITY_1);
    }

    @Test
    public void testProductEqualsAndHashCode() {
        Product product1 = Product.builder()
                .id(PRODUCT_ID_EQUALS)
                .name(PRODUCT_NAME_1)
                .description(PRODUCT_DESCRIPTION_1)
                .price(PRODUCT_PRICE_1)
                .quantity(PRODUCT_QUANTITY_1)
                .build();
        Product product2 = Product.builder()
                .id(PRODUCT_ID_EQUALS)
                .name(PRODUCT_NAME_2)
                .description(PRODUCT_DESCRIPTION_2)
                .price(PRODUCT_PRICE_2)
                .quantity(PRODUCT_QUANTITY_2)
                .build();

        assertThat(product1).isEqualTo(product2);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @Test
    public void testProductNotEquals() {
        Product product1 = Product.builder()
                .id(PRODUCT_ID_1)
                .name(PRODUCT_NAME_1)
                .description(PRODUCT_DESCRIPTION_1)
                .price(PRODUCT_PRICE_1)
                .quantity(PRODUCT_QUANTITY_1)
                .build();
        Product product2 = Product.builder()
                .id(PRODUCT_ID_2)
                .name(PRODUCT_NAME_2)
                .description(PRODUCT_DESCRIPTION_2)
                .price(PRODUCT_PRICE_2)
                .quantity(PRODUCT_QUANTITY_2)
                .build();

        assertThat(product1).isNotEqualTo(product2);
    }
}
