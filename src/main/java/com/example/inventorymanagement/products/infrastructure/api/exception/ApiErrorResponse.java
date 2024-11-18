package com.example.inventorymanagement.products.infrastructure.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private String details;
}
