package com.example.inventorymanagement.products.domain.repository;

import com.example.inventorymanagement.products.domain.model.Product;


public interface ProductCommandRepository {

    Product save(Product product);

    void deleteById(Long id);
}
