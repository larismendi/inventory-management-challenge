package com.example.inventorymanagement.products.infrastructure.inbound.controllers;

import com.example.inventorymanagement.products.application.create.ProductCreateCommand;
import com.example.inventorymanagement.products.application.create.ProductCreateUseCase;
import com.example.inventorymanagement.products.application.delete.ProductDeleteUseCase;
import com.example.inventorymanagement.products.application.find.ProductFindUseCase;
import com.example.inventorymanagement.products.application.update.ProductUpdateCommand;
import com.example.inventorymanagement.products.application.update.ProductUpdateUseCase;
import com.example.inventorymanagement.products.domain.exception.ProductAlreadyExistsException;
import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@DisplayName("ProductController Test")
class ProductControllerTest {

    private static final Long PRODUCT_A_ID = 1L;
    private static final String PRODUCT_A_NAME = "Product A";
    private static final String PRODUCT_A_DESCRIPTION = "Product A Description";
    private static final Double PRODUCT_A_PRICE = 99.99;
    private static final Integer PRODUCT_A_QUANTITY = 10;
    private static final Long PRODUCT_B_ID = 2L;
    private static final String PRODUCT_B_NAME = "Product B";
    private static final String PRODUCT_B_DESCRIPTION = "Product B Description";
    private static final Double PRODUCT_B_PRICE = 49.99;
    private static final Integer PRODUCT_B_QUANTITY = 5;
    private static final String UPDATED_PRODUCT_NAME = "Updated Product";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "Updated Description";
    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %s not found";
    private static final String PRODUCT_NOT_FOUND = "Product not found";
    private static final String PRODUCT_ALREADY_EXISTS = "Product already exists";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private ProductCreateUseCase productCreateUseCase;

    @MockBean
    private ProductFindUseCase productFindUseCase;

    @MockBean
    private ProductUpdateUseCase productUpdateUseCase;

    @MockBean
    private ProductDeleteUseCase productDeleteUseCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
        Product product1 = createTestProductA();
        Product product2 = createTestProductB();

        return List.of(product1, product2);
    }

    @Test
    void testGetAllProductsWhenAllOkThenReturnListOfProducts() throws Exception {
        List<Product> products = createTestProductList();
        when(productFindUseCase.findAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(PRODUCT_A_ID))
                .andExpect(jsonPath("$[0].name").value(PRODUCT_A_NAME))
                .andExpect(jsonPath("$[0].description").value(PRODUCT_A_DESCRIPTION))
                .andExpect(jsonPath("$[0].price").value(PRODUCT_A_PRICE))
                .andExpect(jsonPath("$[0].quantity").value(PRODUCT_A_QUANTITY))
                .andExpect(jsonPath("$[1].id").value(PRODUCT_B_ID))
                .andExpect(jsonPath("$[1].name").value(PRODUCT_B_NAME))
                .andExpect(jsonPath("$[1].description").value(PRODUCT_B_DESCRIPTION))
                .andExpect(jsonPath("$[1].price").value(PRODUCT_B_PRICE))
                .andExpect(jsonPath("$[1].quantity").value(PRODUCT_B_QUANTITY));

        verify(productFindUseCase, times(1)).findAllProducts();
    }

    @Test
    void testGetProductByIdWhenProductFoundedThenReturnOk() throws Exception {
        Product product = createTestProductA();
        when(productFindUseCase.findProductById(PRODUCT_A_ID)).thenReturn(product);

        mockMvc.perform(get("/api/products/" + PRODUCT_A_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PRODUCT_A_ID))
                .andExpect(jsonPath("$.name").value(PRODUCT_A_NAME))
                .andExpect(jsonPath("$.description").value(PRODUCT_A_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(PRODUCT_A_PRICE))
                .andExpect(jsonPath("$.quantity").value(PRODUCT_A_QUANTITY));

        verify(productFindUseCase, times(1)).findProductById(1L);
    }

    @Test
    void testGetProductByIdWhenProductNotFoundThenThrowsProductNotFoundException() throws Exception {
        when(productFindUseCase.findProductById(PRODUCT_A_ID))
                .thenThrow(new ProductNotFoundException(PRODUCT_NOT_FOUND));

        var result = mockMvc.perform(get("/api/products/" + PRODUCT_A_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(PRODUCT_NOT_FOUND));
        verify(productFindUseCase).findProductById(PRODUCT_A_ID);
    }

    @Test
    void testCreateProductWhenProductIsCorrectThenReturnCreatedProduct() throws Exception {
        Product createdProduct = createTestProductA();
        when(productCreateUseCase.createProduct(any(ProductCreateCommand.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(PRODUCT_A_ID))
                .andExpect(jsonPath("$.name").value(PRODUCT_A_NAME))
                .andExpect(jsonPath("$.description").value(PRODUCT_A_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(PRODUCT_A_PRICE))
                .andExpect(jsonPath("$.quantity").value(PRODUCT_A_QUANTITY));

        verify(productCreateUseCase, times(1)).createProduct(any(ProductCreateCommand.class));
    }

    @Test
    void testCreateProductWhenProductExistsThenThrowsProductAlreadyExistsException() throws Exception {
        Product createdProduct = createTestProductA();
        when(productCreateUseCase.createProduct(any(ProductCreateCommand.class)))
                .thenThrow(new ProductAlreadyExistsException(PRODUCT_ALREADY_EXISTS));

        var result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createdProduct)));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(PRODUCT_ALREADY_EXISTS));
        verify(productCreateUseCase, times(1)).createProduct(any(ProductCreateCommand.class));
    }

    @Test
    void testUpdateProductWhenProductExistsThenReturnUpdatedProduct() throws Exception {
        Product updatedProduct = Product.builder()
                .id(PRODUCT_A_ID)
                .name(UPDATED_PRODUCT_NAME)
                .description(UPDATED_PRODUCT_DESCRIPTION)
                .build();
        when(productUpdateUseCase.updateProduct(eq(PRODUCT_A_ID), any(ProductUpdateCommand.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/" + PRODUCT_A_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(UPDATED_PRODUCT_NAME));

        verify(productUpdateUseCase, times(1))
                .updateProduct(eq(PRODUCT_A_ID), any(ProductUpdateCommand.class));
    }

    @Test
    void testUpdateProductWhenProductNotExistsThenThrowsProductNotFoundException() throws Exception {
        Product updatedProduct = createTestProductA();
        when(productUpdateUseCase.updateProduct(eq(PRODUCT_A_ID), any(ProductUpdateCommand.class)))
                .thenThrow(new ProductNotFoundException(PRODUCT_NOT_FOUND));

        var result = mockMvc.perform(put("/api/products/" + PRODUCT_A_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedProduct)));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(PRODUCT_NOT_FOUND));
        verify(productUpdateUseCase, times(1))
                .updateProduct(eq(PRODUCT_A_ID), any(ProductUpdateCommand.class));
    }

    @Test
    void testDeleteProductWhenProductExistsThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/products/" + PRODUCT_A_ID))
                .andExpect(status().isNoContent());

        verify(productDeleteUseCase, times(1))
                .deleteProduct(PRODUCT_A_ID);
    }

    @Test
    void testDeleteProductWhenProductDoesNotExistThenThrowsProductNotFoundException() throws Exception {
        String notFoundDetails = String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, PRODUCT_A_ID);
        doThrow(new ProductNotFoundException(notFoundDetails))
                .when(productDeleteUseCase).deleteProduct(PRODUCT_A_ID);

        var result = mockMvc.perform(delete("/api/products/" + PRODUCT_A_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(PRODUCT_NOT_FOUND))
                .andExpect(jsonPath("$.details").value(notFoundDetails));

        verify(productDeleteUseCase, times(1)).deleteProduct(1L);
    }
}
