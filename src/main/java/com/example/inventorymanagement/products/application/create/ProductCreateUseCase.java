package com.example.inventorymanagement.products.application.create;

import com.example.inventorymanagement.products.domain.exception.ProductAlreadyExistsException;
import com.example.inventorymanagement.products.domain.model.Product;
import com.example.inventorymanagement.products.domain.repository.ProductCommandRepository;
import com.example.inventorymanagement.products.domain.repository.ProductQueryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCreateUseCase {

    private static final String PRODUCT_ALREADY_EXISTS_MESSAGE_TEMPLATE = "Product with name %s already exists.";

    private final ProductQueryRepository productQueryRepository;
    private final ProductCommandRepository productCommandRepository;
    private final ProductCreateMapper productCreateMapper;

    public ProductCreateUseCase(ProductQueryRepository productQueryRepository,
                                ProductCommandRepository productCommandRepository,
                                ProductCreateMapper productCreateMapper) {
        this.productQueryRepository = productQueryRepository;
        this.productCommandRepository = productCommandRepository;
        this.productCreateMapper = productCreateMapper;
    }

    @Transactional
    public Product createProduct(ProductCreateCommand command) {
        if (productQueryRepository.existsByName(command.getName())) {
            throw new ProductAlreadyExistsException(String.format(PRODUCT_ALREADY_EXISTS_MESSAGE_TEMPLATE,
                    command.getName()));
        }

        Product product = productCreateMapper.toEntity(command);

        return productCommandRepository.save(product);
    }
}
