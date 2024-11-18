package com.example.inventorymanagement.products.application.update;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdateUseCase {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %s not found";

    private final ProductUpdateMapper productUpdateMapper;
    private final ProductQueryRepository productQueryRepository;
    private final ProductCommandRepository productCommandRepository;

    public ProductUpdateUseCase(ProductUpdateMapper productUpdateMapper,
                                ProductQueryRepository productQueryRepository,
                                ProductCommandRepository productCommandRepository) {
        this.productUpdateMapper = productUpdateMapper;
        this.productQueryRepository = productQueryRepository;
        this.productCommandRepository = productCommandRepository;
    }

    public Product updateProduct(Long id, ProductUpdateCommand command) {
        return productQueryRepository.findById(id)
                .map(existingProduct -> productUpdateMapper.updateExistingProduct(existingProduct, command))
                .map(productCommandRepository::save)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, id)));
    }
}
