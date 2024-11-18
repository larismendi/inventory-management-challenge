package com.example.inventorymanagement.products.application.delete;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductDeleteUseCaseTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Sample Product";
    private static final String PRODUCT_DESCRIPTION = "Sample Description";
    private static final Double PRODUCT_PRICE = 100.0;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %s not found";

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private ProductCommandRepository productCommandRepository;

    @InjectMocks
    private ProductDeleteUseCase productDeleteUseCase;

    @Test
    void givenExistingProductIdWhenDeleteProductThenDeleteSuccessfully() {
        Product existingProduct = Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        when(productQueryRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(existingProduct));

        productDeleteUseCase.deleteProduct(PRODUCT_ID);

        verify(productQueryRepository).findById(PRODUCT_ID);
        verify(productCommandRepository).deleteById(PRODUCT_ID);
    }

    @Test
    void givenNonExistingProductIdWhenDeleteProductThenThrowsProductNotFoundException() {
        when(productQueryRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        ProductNotFoundException exception = Assertions.assertThrows(ProductNotFoundException.class,
                () -> productDeleteUseCase.deleteProduct(PRODUCT_ID));

        Assertions.assertEquals(
                String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, PRODUCT_ID),
                exception.getMessage()
        );

        verify(productQueryRepository).findById(PRODUCT_ID);
        verify(productCommandRepository, never()).deleteById(any());
    }
}
