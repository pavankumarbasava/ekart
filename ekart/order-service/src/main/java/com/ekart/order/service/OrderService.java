package com.ekart.order.service;

import com.ekart.order.entity.Order;

public interface OrderService {
	public Order buildOrder(String cartCode,String token, String orderCode) throws Exception;
}
