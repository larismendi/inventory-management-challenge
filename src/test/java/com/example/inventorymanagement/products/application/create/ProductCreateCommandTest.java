package com.example.inventorymanagement.products.application.create;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCreateCommandTest {

    private static final String PRODUCT_NAME = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";
    private static final Double PRODUCT_PRICE = 100.0;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final String VIOLATION_MESSAGE = "Should have one violation.";
    private static final String VALIDATE_MESSAGE = "Should be valid when all fields are correct.";
    private static final String INVALID_NAME = "Name should not be blank.";
    private static final String INVALID_DESCRIPTION = "Description should not be blank.";
    private static final String INVALID_PRICE = "Price should not be null.";
    private static final String INVALID_QUANTITY = "Quantity should not be null.";

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing validator", e);
        }
    }

    @Test
    void shouldValidateProductCreateCommand() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductCreateCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty(), VALIDATE_MESSAGE);
    }

    @Test
    void shouldNotValidateWhenNameIsBlank() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name("")
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductCreateCommand>> violations = validator.validate(command);
        assertFalse(violations.isEmpty(), INVALID_NAME);
        assertEquals(1, violations.size(), VIOLATION_MESSAGE);
    }

    @Test
    void shouldNotValidateWhenDescriptionIsBlank() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description("")
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductCreateCommand>> violations = validator.validate(command);
        assertFalse(violations.isEmpty(), INVALID_DESCRIPTION);
        assertEquals(1, violations.size(), VIOLATION_MESSAGE);
    }

    @Test
    void shouldNotValidateWhenPriceIsNull() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(null)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductCreateCommand>> violations = validator.validate(command);
        assertFalse(violations.isEmpty(), INVALID_PRICE);
        assertEquals(1, violations.size(), VIOLATION_MESSAGE);
    }

    @Test
    void shouldNotValidateWhenQuantityIsNull() {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(null)
                .build();

        Set<ConstraintViolation<ProductCreateCommand>> violations = validator.validate(command);
        assertFalse(violations.isEmpty(), INVALID_QUANTITY);
        assertEquals(1, violations.size(), VIOLATION_MESSAGE);
    }
}
