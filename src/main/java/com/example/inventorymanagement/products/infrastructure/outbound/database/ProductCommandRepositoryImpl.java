package com.example.inventorymanagement.products.infrastructure.outbound.database;

import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.infrastructure.mapper.ProductMapper;
import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;
import com.example.inventorymanagement.products.infrastructure.outbound.database.repository.JpaProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
public class ProductCommandRepositoryImpl implements ProductCommandRepository {

    private final JpaProductRepository jpaRepository;

    public ProductCommandRepositoryImpl(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Transactional
    @Override
    public Product save(Product product) {
        ProductEntity productEntity = jpaRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
