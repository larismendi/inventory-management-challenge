package com.example.inventorymanagement.products.infrastructure.outbound.database;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import com.example.inventorymanagement.products.infrastructure.mapper.ProductMapper;
import com.example.inventorymanagement.products.infrastructure.outbound.database.repository.JpaProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JpaProductRepository jpaRepository;

    public ProductQueryRepositoryImpl(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}
