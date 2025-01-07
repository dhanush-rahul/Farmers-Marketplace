/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.order_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dhanu
 */
@FeignClient(name = "product-service", url="http://localhost:8091/api/product")
public interface ProductClient {

    @PutMapping("/{productId}/stock/reduce")
    void reduceStock(@PathVariable int productId, @RequestParam int quantity);

    @PutMapping("/{productId}/stock/restore")
    void restoreStock(@PathVariable int productId, @RequestParam int quantity);
}
