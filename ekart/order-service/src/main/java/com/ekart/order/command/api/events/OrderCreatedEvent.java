package com.ekart.order.command.api.events;

import com.ekart.commonservice.dto.PaymentDto;
import com.ekart.order.entity.Order;

import lombok.Data;

@Data
public class OrderCreatedEvent {

	private String orderCode;
    private String  cartCode;
    private String orderStatus;
    private String token;
    private Long userId;
    private PaymentDto payment;
    private Order order;
    private String errorContext;

   
}
