/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.order_service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author dhanu
 */

 @Service
 @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Order createOrder(Order order) {
        productClient.reduceStock(order.getProductId(), order.getQuantity());
        return orderRepository.save(order);
    }
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Restore stock on order cancellation
        productClient.restoreStock(order.getProductId(), order.getQuantity());
        orderRepository.deleteById(orderId);
    }
    public List<Order> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    // Retrieve all orders for a farmer
    public List<Order> getOrdersByFarmer(Integer farmerId) {
        return orderRepository.findByFarmerId(farmerId);
    }

    // Retrieve sales history for a farmer (only completed orders)
    public List<Order> getSalesHistoryForFarmer(Integer farmerId) {
        return orderRepository.findSalesHistoryByFarmerId(farmerId);
    }
}
