package com.example.inventorymanagement.products.infrastructure.outbound.database;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.infrastructure.mapper.ProductMapper;
import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;
import com.example.inventorymanagement.products.infrastructure.outbound.database.repository.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductQueryRepositoryImplTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Test Product";
    private static final String PRODUCT_DESCRIPTION = "Test Description";
    private static final Double PRODUCT_PRICE = 99.99;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final Long PRODUCT_ID_ANOTHER = 2L;
    private static final String PRODUCT_NAME_ANOTHER = "Another Product";
    private static final String PRODUCT_DESCRIPTION_ANOTHER = "Another Description";
    private static final Double PRODUCT_PRICE_ANOTHER = 49.99;
    private static final Integer PRODUCT_QUANTITY_ANOTHER = 5;
    private static final String NONEXISTENT_PRODUCT_NAME = "Nonexistent Product";

    private static final Logger logger = LoggerFactory.getLogger(ProductQueryRepositoryImplTest.class);

    @Mock
    private JpaProductRepository jpaProductRepository;

    @InjectMocks
    private ProductQueryRepositoryImpl productQueryRepositoryImpl;

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
    public void testFindByIdWhenProductExistsThenAllOk() {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        when(jpaProductRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));

        Optional<Product> result = productQueryRepositoryImpl.findById(PRODUCT_ID);

        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
        assertEquals(product.getName(), result.get().getName());
        assertEquals(product.getDescription(), result.get().getDescription());
        assertEquals(product.getPrice(), result.get().getPrice());
        assertEquals(product.getQuantity(), result.get().getQuantity());
        verify(jpaProductRepository, times(1)).findById(PRODUCT_ID);
    }

    @Test
    public void testFindByIdWhenProductNotExistsThenReturnEmpty() {
        when(jpaProductRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        Optional<Product> result = productQueryRepositoryImpl.findById(PRODUCT_ID);

        assertFalse(result.isPresent());
        verify(jpaProductRepository, times(1)).findById(PRODUCT_ID);
    }

    @Test
    public void testFindAllWhenProductExistsThenAllOk() {
        ProductEntity productEntity1 = ProductMapper.toEntity(product);
        ProductEntity productEntity2 = ProductEntity.builder()
                .id(PRODUCT_ID_ANOTHER)
                .name(PRODUCT_NAME_ANOTHER)
                .description(PRODUCT_DESCRIPTION_ANOTHER)
                .price(PRODUCT_PRICE_ANOTHER)
                .quantity(PRODUCT_QUANTITY_ANOTHER)
                .build();
        when(jpaProductRepository.findAll()).thenReturn(List.of(productEntity1, productEntity2));

        List<Product> products = productQueryRepositoryImpl.findAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(PRODUCT_NAME, products.get(0).getName());
        assertEquals(PRODUCT_NAME_ANOTHER, products.get(1).getName());
        verify(jpaProductRepository, times(1)).findAll();
    }

    @Test
    public void testExistsByNameWhenProductExistsThenReturnTrue() {
        when(jpaProductRepository.existsByName(PRODUCT_NAME)).thenReturn(true);

        boolean result = productQueryRepositoryImpl.existsByName(PRODUCT_NAME);

        assertTrue(result);
        verify(jpaProductRepository, times(1)).existsByName(PRODUCT_NAME);
    }

    @Test
    public void testExistsByNameWhenProductDoesNotExistThenReturnFalse() {
        when(jpaProductRepository.existsByName(NONEXISTENT_PRODUCT_NAME)).thenReturn(false);

        boolean result = productQueryRepositoryImpl.existsByName(NONEXISTENT_PRODUCT_NAME);

        assertFalse(result);
        verify(jpaProductRepository, times(1)).existsByName(NONEXISTENT_PRODUCT_NAME);
    }
}
