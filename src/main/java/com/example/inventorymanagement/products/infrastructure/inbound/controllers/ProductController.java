package com.example.inventorymanagement.products.infrastructure.inbound.controllers;

import com.example.inventorymanagement.products.application.create.ProductCreateCommand;
import com.example.inventorymanagement.products.application.create.ProductCreateUseCase;
import com.example.inventorymanagement.products.application.delete.ProductDeleteUseCase;
import com.example.inventorymanagement.products.application.find.ProductFindUseCase;
import com.example.inventorymanagement.products.application.update.ProductUpdateCommand;
import com.example.inventorymanagement.products.application.update.ProductUpdateUseCase;
import com.example.inventorymanagement.products.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductCreateUseCase productCreateUseCase;
    private final ProductFindUseCase productFindUseCase;
    private final ProductUpdateUseCase productUpdateUseCase;
    private final ProductDeleteUseCase productDeleteUseCase;

    @Autowired
    public ProductController(ProductCreateUseCase productCreateUseCase, ProductFindUseCase productFindUseCase,
                             ProductUpdateUseCase productUpdateUseCase, ProductDeleteUseCase productDeleteUseCase) {
        this.productCreateUseCase = productCreateUseCase;
        this.productFindUseCase = productFindUseCase;
        this.productUpdateUseCase = productUpdateUseCase;
        this.productDeleteUseCase = productDeleteUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productFindUseCase.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productFindUseCase.findProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductCreateCommand command) {
        return productCreateUseCase.createProduct(command);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateCommand command) {
        return ResponseEntity.ok(productUpdateUseCase.updateProduct(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productDeleteUseCase.deleteProduct(id);
    }
}
