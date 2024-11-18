package com.example.inventorymanagement.products.application.create;

import com.example.inventorymanagement.products.domain.exception.ProductAlreadyExistsException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductCreateUseCaseTest {

    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Product Description";
    private static final Double PRODUCT_PRICE = 49.99;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final Boolean PRODUCT_NOT_EXISTS = false;
    private static final String PRODUCT_ALREADY_EXISTS_MESSAGE_TEMPLATE = "Product with name %s already exists.";

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private ProductCreateMapper productCreateMapper;

    @Mock
    private ProductCommandRepository productCommandRepository;

    @InjectMocks
    private ProductCreateUseCase productCreateUseCase;

    @Test
    public void testCreateProductWhenProductDoesNotExistIsCreatedCorrectly() {
        Product product = Product.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        when(productQueryRepository.existsByName(PRODUCT_NAME)).thenReturn(PRODUCT_NOT_EXISTS);
        when(productCreateMapper.toEntity(any(ProductCreateCommand.class))).thenReturn(product);
        when(productCommandRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productCreateUseCase.createProduct(command);

        assertNotNull(createdProduct);
        assertEquals(PRODUCT_NAME, createdProduct.getName());
        assertEquals(PRODUCT_DESCRIPTION, createdProduct.getDescription());
        assertEquals(PRODUCT_PRICE, createdProduct.getPrice());
        assertEquals(PRODUCT_QUANTITY, createdProduct.getQuantity());

        verify(productQueryRepository, times(1)).existsByName(PRODUCT_NAME);
        verify(productCreateMapper, times(1)).toEntity(any(ProductCreateCommand.class));
        verify(productCommandRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProductWhenProductAlreadyExistsThrowsException() {
        ProductCreateCommand commandWithExistingName = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();
        when(productQueryRepository.existsByName(commandWithExistingName.getName())).thenReturn(true);

        ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class,
                () -> productCreateUseCase.createProduct(commandWithExistingName));

        assertEquals(String.format(PRODUCT_ALREADY_EXISTS_MESSAGE_TEMPLATE, PRODUCT_NAME), exception.getMessage());
        verify(productCommandRepository, never()).save(any());
    }
}
