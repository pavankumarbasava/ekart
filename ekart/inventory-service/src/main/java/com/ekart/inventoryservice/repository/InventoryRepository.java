package com.ekart.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.inventoryservice.entity.Product;



public interface InventoryRepository extends JpaRepository<Product, Integer> {

}
