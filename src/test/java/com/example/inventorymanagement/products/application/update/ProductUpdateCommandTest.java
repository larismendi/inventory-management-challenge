package com.example.inventorymanagement.products.application.update;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductUpdateCommandTest {

    private static final String PRODUCT_NAME = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";
    private static final Double PRODUCT_PRICE = 100.0;
    private static final Integer PRODUCT_QUANTITY = 10;
    private static final String VALID_MESSAGE = "Expected no constraint violations";
    private static final Integer EXPECTED_VIOLATION_COUNT = 1;
    private static final String NOT_NULL_MESSAGE = "must not be null";
    private static final String NOT_BLANK_MESSAGE = "must not be blank";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_QUANTITY = "quantity";

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
    void testValidProductUpdateCommand() {
        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductUpdateCommand>> violations = validator.validate(command);

        assertTrue(violations.isEmpty(), VALID_MESSAGE);
    }

    @Test
    void testInvalidProductUpdateCommandWhenNameIsBlank() {
        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(" ")
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductUpdateCommand>> violations = validator.validate(command);

        assertEquals(EXPECTED_VIOLATION_COUNT, violations.size());
        ConstraintViolation<ProductUpdateCommand> violation = violations.iterator().next();
        assertEquals(FIELD_NAME, violation.getPropertyPath().toString());
        assertEquals(NOT_BLANK_MESSAGE, violation.getMessage());
    }

    @Test
    void testInvalidProductUpdateCommandWhenDescriptionIsBlank() {
        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME)
                .description(" ")
                .price(PRODUCT_PRICE)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductUpdateCommand>> violations = validator.validate(command);

        assertEquals(EXPECTED_VIOLATION_COUNT, violations.size());
        ConstraintViolation<ProductUpdateCommand> violation = violations.iterator().next();
        assertEquals(FIELD_DESCRIPTION, violation.getPropertyPath().toString());
        assertEquals(NOT_BLANK_MESSAGE, violation.getMessage());
    }

    @Test
    void testInvalidProductUpdateCommandWhenPriceIsNull() {
        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(null)
                .quantity(PRODUCT_QUANTITY)
                .build();

        Set<ConstraintViolation<ProductUpdateCommand>> violations = validator.validate(command);

        assertEquals(EXPECTED_VIOLATION_COUNT, violations.size());
        ConstraintViolation<ProductUpdateCommand> violation = violations.iterator().next();
        assertEquals(FIELD_PRICE, violation.getPropertyPath().toString());
        assertEquals(NOT_NULL_MESSAGE, violation.getMessage());
    }

    @Test
    void testInvalidProductUpdateCommandWhenQuantityIsNull() {
        ProductUpdateCommand command = ProductUpdateCommand.builder()
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .quantity(null)
                .build();

        Set<ConstraintViolation<ProductUpdateCommand>> violations = validator.validate(command);

        assertEquals(EXPECTED_VIOLATION_COUNT, violations.size());
        ConstraintViolation<ProductUpdateCommand> violation = violations.iterator().next();
        assertEquals(FIELD_QUANTITY, violation.getPropertyPath().toString());
        assertEquals(NOT_NULL_MESSAGE, violation.getMessage());
    }
}
