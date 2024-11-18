package com.example.inventorymanagement.products.application.delete;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleteUseCase {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %s not found";

    private final ProductQueryRepository productQueryRepository;
    private final ProductCommandRepository productCommandRepository;

    public ProductDeleteUseCase(ProductQueryRepository productQueryRepository,
                                ProductCommandRepository productCommandRepository) {
        this.productQueryRepository = productQueryRepository;
        this.productCommandRepository = productCommandRepository;
    }

    public void deleteProduct(Long id) {
        productQueryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, id)));

        productCommandRepository.deleteById(id);
    }
}
