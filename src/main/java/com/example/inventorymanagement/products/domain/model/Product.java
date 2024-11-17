package com.example.inventorymanagement.products.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
public class Product {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
