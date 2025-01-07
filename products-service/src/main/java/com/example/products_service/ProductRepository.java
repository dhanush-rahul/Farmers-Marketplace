/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.products_service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;


/**
 *
 * @author dhanu
 */
public interface ProductRepository  extends JpaRepository<Product, String>{

    List<Product> findByUserId(Integer userId);

    @Query("SELECT p FROM Product p WHERE (:location IS NULL OR p.location = :location)"+
    "AND (:category IS NULL OR p.category = :category)"+
    "AND (:minPrice IS NULL OR p.price >= :minPrice)"+
    "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findFilteredProducts(
        @Param("location") String location,
        @Param("category") String category,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.availableUnits = p.availableUnits - :quantity WHERE p.id = :productId AND p.availableUnits >= :quantity")
    int reduceStock(@Param("productId") String productId, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.availableUnits = p.availableUnits + :quantity WHERE p.id = :productId")
    void restoreStock(@Param("productId") String productId, @Param("quantity") int quantity);

}
