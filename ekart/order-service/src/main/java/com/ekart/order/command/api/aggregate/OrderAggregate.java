package com.ekart.order.command.api.aggregate;


import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.ekart.commonservice.commands.CancelOrderCommand;
import com.ekart.commonservice.commands.CompleteOrderCommand;
import com.ekart.commonservice.dto.PaymentDto;
import com.ekart.commonservice.events.OrderCancelledEvent;
import com.ekart.commonservice.events.OrderCompletedEvent;
import com.ekart.order.command.api.command.CreateOrderCommand;
import com.ekart.order.command.api.events.OrderCreatedEvent;
import com.ekart.order.command.api.events.OrderEventsHandler;
import com.ekart.order.entity.Order;
import com.ekart.order.service.impl.OrderServiceImpl;

@Aggregate
public class OrderAggregate {

	
	@Inject
	
    private OrderServiceImpl service;

	@AggregateIdentifier
    private String orderCode;
	 private String  cartCode;
     private String orderStatus;
     private String token;
     private Long userId;
     private PaymentDto payment;
     private Order order;
    
//    private Long userId;
   

    public OrderAggregate() {
    }



    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand,OrderServiceImpl service) throws Exception {
        //Validate The Command
    	  Order  buildOrder = service.buildOrder(createOrderCommand.getCartCode(),createOrderCommand.getToken(),
    			   createOrderCommand.getOrderCode());
        OrderCreatedEvent orderCreatedEvent
                = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand,
                orderCreatedEvent);
        orderCreatedEvent.setOrder(buildOrder);
        orderCreatedEvent.setUserId(buildOrder.getUser());
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event,OrderEventsHandler handler) throws Exception {
        this.orderStatus = event.getOrderStatus();
        this.cartCode = event.getCartCode();
        this.orderCode=event.getOrderCode();
        this.token=event.getToken();
        this.userId=event.getUserId();
        this.payment=event.getPayment();
        this.order=event.getOrder();
        handler.orderCreateHandler(event);
        
        	
        
        
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        //Validate The Command
        // Publish Order Completed Event
        OrderCompletedEvent orderCompletedEvent
                = OrderCompletedEvent.builder()
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderCode(completeOrderCommand.getOrderCode())
                .build();
        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.orderStatus = event.getOrderStatus();
       
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        OrderCancelledEvent orderCancelledEvent
                = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand,
                orderCancelledEvent);

        AggregateLifecycle.apply(orderCancelledEvent);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.orderStatus = event.getOrderStatus();
    }
}
