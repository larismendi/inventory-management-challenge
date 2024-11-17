package com.example.inventorymanagement.products.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ProductNotFoundExceptionTest {

    private static final String expectedMessage = "Product not found";

    @Test
    void shouldThrowProductNotFoundExceptionWithCorrectMessage() {

        ProductNotFoundException exception = catchThrowableOfType(() -> {
            throw new ProductNotFoundException(expectedMessage);
        }, ProductNotFoundException.class);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}
