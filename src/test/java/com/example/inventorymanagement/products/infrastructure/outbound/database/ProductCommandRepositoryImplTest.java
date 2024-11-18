package com.example.inventorymanagement.products.infrastructure.outbound.database;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.infrastructure.mapper.ProductMapper;
import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;
import com.example.inventorymanagement.products.infrastructure.outbound.database.repository.JpaProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductCommandRepositoryImplTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final Double PRODUCT_PRICE = 99.99;
    private static final Integer PRODUCT_QUANTITY = 10;

    private static final Logger logger = LoggerFactory.getLogger(ProductCommandRepositoryImplTest.class);

    @Mock
    private JpaProductRepository jpaProductRepository;

    @InjectMocks
    private ProductCommandRepositoryImpl productCommandRepositoryImpl;

    private Product product;

    @BeforeEach
    public void setUp() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            product = Product.builder()
                    .id(PRODUCT_ID)
                    .name(PRODUCT_NAME)
                    .description(PRODUCT_DESCRIPTION)
                    .price(PRODUCT_PRICE)
                    .quantity(PRODUCT_QUANTITY)
                    .build();
        } catch (Exception e) {
            logger.error("Error initializing mocks: ", e);
        }
    }

    @Test
    @Transactional
    public void testSaveWhenProductEntityIsValidThenPersistedCorrectly() {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        when(jpaProductRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        Product savedProduct = productCommandRepositoryImpl.save(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
        verify(jpaProductRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    @Transactional
    public void testDeleteByIdWhenProductIdIsValidThenDeletedCorrectly() {
        productCommandRepositoryImpl.deleteById(PRODUCT_ID);

        verify(jpaProductRepository, times(1)).deleteById(PRODUCT_ID);
    }
}
