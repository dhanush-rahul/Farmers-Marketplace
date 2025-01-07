/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.products_service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author dhanu
 */

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Product product){
        productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.listAllProducts();
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> getUserById(@PathVariable String productId){
        return productService.getProductById(productId).map(user -> ResponseEntity.ok(user))
               .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{userId}/products")
    public ResponseEntity<List<Product>> getProductsForUser(@PathVariable Integer userId){
        List<Product> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);

    }

    @GetMapping("/filter")
    public Page<Product> filterProducts(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return productService.filterProducts(location, category, minPrice, maxPrice, page, size);
    }
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }   

     @PutMapping("/{productId}/status")
    public ResponseEntity<Product> updateProductStatus(
            @PathVariable String productId,
            @RequestParam String status) {
        Product updatedProduct = productService.updateProductStatus(productId, status);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{productId}/stock")
    public ResponseEntity<Product> updateAvailableUnits(
            @PathVariable String productId,
            @RequestParam int availableUnits) {
        productService.updateAvailableUnits(productId, availableUnits);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/stock/reduce")
public ResponseEntity<Void> reduceStock(
        @PathVariable String productId,
        @RequestParam int quantity) {
    productService.reduceStock(productId, quantity);
    return ResponseEntity.ok().build();
}

@PutMapping("/{productId}/stock/restore")
public ResponseEntity<Void> restoreStock(
        @PathVariable String productId,
        @RequestParam int quantity) {
    productService.restoreStock(productId, quantity);
    return ResponseEntity.ok().build();
}
}
