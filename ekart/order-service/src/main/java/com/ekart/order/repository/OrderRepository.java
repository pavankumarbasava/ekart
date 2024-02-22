package com.ekart.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	
	@Query("select o from Order o where o.orderCode=:orderCode")
	Order findByOrderCode(@Param("orderCode") String orderCode);

}
