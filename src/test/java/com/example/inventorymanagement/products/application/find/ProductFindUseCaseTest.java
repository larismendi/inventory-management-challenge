package com.example.inventorymanagement.products.application.find;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductFindUseCaseTest {

    private static final Long PRODUCT_A_ID = 1L;
    private static final Long PRODUCT_B_ID = 2L;
    private static final String PRODUCT_A_NAME = "Product A";
    private static final String PRODUCT_B_NAME = "Product B";
    private static final String PRODUCT_A_DESCRIPTION = "Product A Description";
    private static final String PRODUCT_B_DESCRIPTION = "Product B Description";
    private static final Double PRODUCT_A_PRICE = 99.99;
    private static final Double PRODUCT_B_PRICE = 49.99;
    private static final Integer PRODUCT_A_QUANTITY = 10;
    private static final Integer PRODUCT_B_QUANTITY = 5;
    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %S not found";

    @Mock
    private ProductQueryRepository productQueryRepository;

    @InjectMocks
    private ProductFindUseCase productFindUseCase;

    @BeforeEach
    void setUp() {
        productQueryRepository = mock(ProductQueryRepository.class);

        productFindUseCase = new ProductFindUseCase(productQueryRepository);
    }

    private static Product createTestProductA() {
        return Product.builder()
                .id(PRODUCT_A_ID)
                .name(PRODUCT_A_NAME)
                .description(PRODUCT_A_DESCRIPTION)
                .price(PRODUCT_A_PRICE)
                .quantity(PRODUCT_A_QUANTITY)
                .build();
    }

    private static Product createTestProductB() {
        return Product.builder()
                .id(PRODUCT_B_ID)
                .name(PRODUCT_B_NAME)
                .description(PRODUCT_B_DESCRIPTION)
                .price(PRODUCT_B_PRICE)
                .quantity(PRODUCT_B_QUANTITY)
                .build();
    }

    private static List<Product> createTestProductList() {
        Product productA = createTestProductA();
        Product productB = createTestProductB();

        return List.of(productA, productB);
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = createTestProductList();
        when(productQueryRepository.findAll()).thenReturn(products);

        List<Product> result = productFindUseCase.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(PRODUCT_A_NAME, result.get(0).getName());
        assertEquals(PRODUCT_A_DESCRIPTION, result.get(0).getDescription());
        assertEquals(PRODUCT_A_PRICE, result.get(0).getPrice());
        assertEquals(PRODUCT_A_QUANTITY, result.get(0).getQuantity());
        assertEquals(PRODUCT_B_NAME, result.get(1).getName());
        assertEquals(PRODUCT_B_DESCRIPTION, result.get(1).getDescription());
        assertEquals(PRODUCT_B_PRICE, result.get(1).getPrice());
        assertEquals(PRODUCT_B_QUANTITY, result.get(1).getQuantity());
    }

    @Test
    void testFindProductByIdWhenProductExists() {
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .name(PRODUCT_A_NAME)
                .description(PRODUCT_A_DESCRIPTION)
                .price(PRODUCT_A_PRICE)
                .quantity(PRODUCT_A_QUANTITY)
                .build();
        when(productQueryRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productFindUseCase.findProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals(PRODUCT_A_NAME, result.getName());
    }

    @Test
    void testFindProductByIdWhenProductNotExistsThenThrowsException() {
        Long productId = 999L;

        when(productQueryRepository.findById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException thrown =
                assertThrows(ProductNotFoundException.class, () -> productFindUseCase.findProductById(productId));

        assertEquals(String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, productId), thrown.getMessage());
    }
}
