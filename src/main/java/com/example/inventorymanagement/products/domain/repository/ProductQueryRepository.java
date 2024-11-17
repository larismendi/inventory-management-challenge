package com.example.inventorymanagement.products.domain.repository;

import com.example.inventorymanagement.products.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductQueryRepository {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    boolean existsByName(String name);
}
