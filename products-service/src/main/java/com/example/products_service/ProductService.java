/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.products_service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author dhanu
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> listAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByUserId(Integer userId) {
        return productRepository.findByUserId(userId);

    }

    public Page<Product> filterProducts(String location, String category, Double minPrice, Double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findFilteredProducts(location, category, minPrice, maxPrice, pageable);
    }
    public void deleteProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }
    public Product updateProductStatus(String productId, String status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(status);
        return productRepository.save(product);
    }
    public void reduceStock(String productId, int quantity) {
        int updatedRows = productRepository.reduceStock(productId, quantity);
        if (updatedRows == 0) {
            throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }
    }

    public void restoreStock(String productId, int quantity) {
        productRepository.restoreStock(productId, quantity);
    }

    public void updateAvailableUnits(String productId, int availableUnits) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAvailableUnits(availableUnits);
        productRepository.save(product);
    }

}
