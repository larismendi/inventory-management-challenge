package com.example.inventorymanagement.products.infrastructure.api.exception;

import com.example.inventorymanagement.products.domain.exception.ProductAlreadyExistsException;
import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Product not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Product already exists", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
