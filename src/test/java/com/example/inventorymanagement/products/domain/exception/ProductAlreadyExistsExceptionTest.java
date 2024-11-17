package com.example.inventorymanagement.products.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ProductAlreadyExistsExceptionTest {

    private static final String expectedMessage = "Product not found";

    @Test
    void shouldThrowProductAlreadyExistsExceptionWithCorrectMessage() {

        ProductAlreadyExistsException exception = catchThrowableOfType(() -> {
            throw new ProductAlreadyExistsException(expectedMessage);
        }, ProductAlreadyExistsException.class);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}
