package com.example.inventorymanagement.products.application.update;

import com.example.inventorymanagement.products.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductUpdateMapperTest {

    private static final Long PRODUCT_ID_OLD = 1L;
    private static final String PRODUCT_NAME_OLD = "Old Name";
    private static final String PRODUCT_DESCRIPTION_OLD = "Old Description";
    private static final Double PRODUCT_PRICE_OLD = 50.0;
    private static final Integer PRODUCT_QUANTITY_OLD = 10;
    private static final String PRODUCT_NAME_NEW = "New Name";
    private static final String PRODUCT_DESCRIPTION_NEW = "New Description";
    private static final Double PRODUCT_PRICE_NEW = 100.0;
    private static final Integer PRODUCT_QUANTITY_NEW = 20;

    private ProductUpdateMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductUpdateMapper();
    }

    @Test
    void updateExistingProduct_WhenGivenValidInput_UpdatesFieldsCorrectly() {
        Product existingProduct = Product.builder()
                .id(PRODUCT_ID_OLD)
                .name(PRODUCT_NAME_OLD)
                .description(PRODUCT_DESCRIPTION_OLD)
                .price(PRODUCT_PRICE_OLD)
                .quantity(PRODUCT_QUANTITY_OLD)
                .createdAt(LocalDateTime.of(2023, 1, 1, 10, 0))
                .updatedAt(LocalDateTime.of(2023, 1, 1, 10, 0))
                .build();

        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME_NEW)
                .description(PRODUCT_DESCRIPTION_NEW)
                .price(PRODUCT_PRICE_NEW)
                .quantity(PRODUCT_QUANTITY_NEW)
                .build();

        Product updatedProduct = mapper.updateExistingProduct(existingProduct, command);

        assertThat(updatedProduct.getId()).isEqualTo(PRODUCT_ID_OLD);
        assertThat(updatedProduct.getName()).isEqualTo(PRODUCT_NAME_NEW);
        assertThat(updatedProduct.getDescription()).isEqualTo(PRODUCT_DESCRIPTION_NEW);
        assertThat(updatedProduct.getPrice()).isEqualTo(PRODUCT_PRICE_NEW);
        assertThat(updatedProduct.getQuantity()).isEqualTo(PRODUCT_QUANTITY_NEW);
        assertThat(updatedProduct.getCreatedAt()).isEqualTo(existingProduct.getCreatedAt());
        assertThat(updatedProduct.getUpdatedAt()).isNotNull();
        assertThat(updatedProduct.getUpdatedAt()).isAfter(existingProduct.getUpdatedAt());
    }
}
