package com.ekart.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
