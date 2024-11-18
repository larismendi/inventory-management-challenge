package com.example.inventorymanagement.products.application.update;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductUpdateUseCaseTest {

    private static final Long PRODUCT_ID_OLD = 1L;
    private static final String PRODUCT_NAME_OLD = "Old Name";
    private static final String PRODUCT_DESCRIPTION_OLD = "Old Description";
    private static final Double PRODUCT_PRICE_OLD = 50.0;
    private static final Integer PRODUCT_QUANTITY_OLD = 10;
    private static final String PRODUCT_NAME_NEW = "New Name";
    private static final String PRODUCT_DESCRIPTION_NEW = "New Description";
    private static final Double PRODUCT_PRICE_NEW = 100.0;
    private static final Integer PRODUCT_QUANTITY_NEW = 20;
    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %s not found";

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private ProductCommandRepository productCommandRepository;

    @Mock
    private ProductUpdateMapper productUpdateMapper;

    private ProductUpdateCommand command;
    private ProductUpdateUseCase useCase;

    @BeforeEach
    void setUp() {
        command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME_NEW)
                .description(PRODUCT_DESCRIPTION_NEW)
                .price(PRODUCT_PRICE_NEW)
                .quantity(PRODUCT_QUANTITY_NEW)
                .build();
        useCase = new ProductUpdateUseCase(productUpdateMapper, productQueryRepository, productCommandRepository);
    }

    @Test
    void updateProductWhenProductExistsUpdatesProductCorrectly() {

        Product existingProduct = Product.builder()
                .id(PRODUCT_ID_OLD)
                .name(PRODUCT_NAME_OLD)
                .description(PRODUCT_DESCRIPTION_OLD)
                .price(PRODUCT_PRICE_OLD)
                .quantity(PRODUCT_QUANTITY_OLD)
                .createdAt(LocalDateTime.of(2023, 1, 1, 10, 0))
                .updatedAt(LocalDateTime.of(2023, 1, 1, 10, 0))
                .build();
        Product updatedProduct = Product.builder()
                .id(PRODUCT_ID_OLD)
                .name(PRODUCT_NAME_NEW)
                .description(PRODUCT_DESCRIPTION_NEW)
                .price(PRODUCT_PRICE_NEW)
                .quantity(PRODUCT_QUANTITY_NEW)
                .createdAt(existingProduct.getCreatedAt())
                .updatedAt(LocalDateTime.of(2023, 1, 2, 10, 0))
                .build();
        when(productQueryRepository.findById(PRODUCT_ID_OLD)).thenReturn(Optional.of(existingProduct));
        when(productUpdateMapper.updateExistingProduct(existingProduct, command)).thenReturn(updatedProduct);
        when(productCommandRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = useCase.updateProduct(PRODUCT_ID_OLD, command);

        assertThat(result).isEqualTo(updatedProduct);
        verify(productQueryRepository).findById(PRODUCT_ID_OLD);
        verify(productUpdateMapper).updateExistingProduct(existingProduct, command);
        verify(productCommandRepository).save(updatedProduct);
    }

    @Test
    void updateProductWhenProductDoesNotExistThrowsException() {

        when(productQueryRepository.findById(PRODUCT_ID_OLD)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.updateProduct(PRODUCT_ID_OLD, command))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, PRODUCT_ID_OLD));

        verify(productQueryRepository).findById(PRODUCT_ID_OLD);
        verifyNoInteractions(productUpdateMapper, productCommandRepository);
    }
}
