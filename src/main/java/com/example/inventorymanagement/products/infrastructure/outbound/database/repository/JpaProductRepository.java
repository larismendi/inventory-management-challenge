package com.example.inventorymanagement.products.infrastructure.outbound.database.repository;

import com.example.inventorymanagement.products.infrastructure.outbound.database.entity.ProductEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(@NotNull @Size(min = 1, max = 100) String name);
}
