/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.order_service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author dhanu
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByFarmerId(Integer farmerId);
    List<Order> findByCustomerId(Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.farmerId = :farmerId AND o.status = 'DELIVERED'")
    List<Order> findSalesHistoryByFarmerId(@Param("farmerId") Integer farmerId);

}
