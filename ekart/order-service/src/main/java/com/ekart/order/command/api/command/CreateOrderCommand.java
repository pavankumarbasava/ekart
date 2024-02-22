package com.ekart.order.command.api.command
;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.ekart.commonservice.dto.PaymentDto;
import com.ekart.order.entity.Order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {

     @TargetAggregateIdentifier
     private String orderCode;
      private String  cartCode;
      private String orderStatus;
      private String token;
      private Long userId;
      private PaymentDto payment;
      private Order order;
      
}


