package com.example.inventorymanagement.products.application.find;

import com.example.inventorymanagement.products.domain.exception.ProductNotFoundException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFindUseCase {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE = "Product with ID %S not found";

    private final ProductQueryRepository productQueryRepository;

    @Autowired
    public ProductFindUseCase(ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    public List<Product> findAllProducts() {
        return productQueryRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productQueryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format(PRODUCT_NOT_FOUND_EXCEPTION_TEMPLATE, id)));
    }
}
