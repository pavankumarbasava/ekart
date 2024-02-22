package com.ekart.order.command.api.saga;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.ekart.commonservice.commands.CancelOrderCommand;
import com.ekart.commonservice.commands.CancelPaymentCommand;
import com.ekart.commonservice.commands.CompleteOrderCommand;
import com.ekart.commonservice.commands.InventoryQuantityRevertCommand;
import com.ekart.commonservice.commands.InventoryQuantityUpdateCommand;
import com.ekart.commonservice.commands.ValidatePaymentCommand;
import com.ekart.commonservice.dto.ProductSagaDto;
import com.ekart.commonservice.events.InventoryQuantityRevertEvent;
import com.ekart.commonservice.events.InventoryQuantityUpdateEvent;
import com.ekart.commonservice.events.OrderCancelledEvent;
import com.ekart.commonservice.events.OrderCompletedEvent;
import com.ekart.commonservice.events.PaymentCancelledEvent;
import com.ekart.commonservice.events.PaymentProcessedEvent;
import com.ekart.order.command.api.events.OrderCreatedEvent;
import com.ekart.order.entity.Order;
import com.ekart.order.entity.OrderItem;
import com.ekart.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderProcessingSaga {

	private Long userId;
	private Double discountedPrice;
	 private List<ProductSagaDto> orderedProducts;
	
	
    @Inject
    private transient CommandGateway commandGateway;


	@Autowired
    private OrderRepository orderRepository;

    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderCode")
    private void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for Order Id : {}",
                event.getOrder());

        
     
		try {
			Order byOrderCode = orderRepository.findByOrderCode(event.getOrderCode());
			if (byOrderCode == null) {
				throw new Exception("Order not found exception from saga");
			}
			
           InventoryQuantityUpdateCommand inventoryQuantityUpdateCommand = 
        		   new InventoryQuantityUpdateCommand();
         
           List<ProductSagaDto> productList = byOrderCode.getOrderItems().stream().map(
        		  ( OrderItem  orderItem)->{
               	  
              return new ProductSagaDto( orderItem.getProductId(), orderItem.getQuantity());
               	   
                  }).distinct().toList();
           
           this.userId=byOrderCode.getUser();
           this.discountedPrice=byOrderCode.getTotalDiscountedPrice();
        
           inventoryQuantityUpdateCommand.setOrderCode(byOrderCode.getOrderCode());
      //     inventoryQuantityUpdateCommand.setProducts(productList);
          this.orderedProducts=productList;
//        	 
//        	
//            ValidatePaymentCommand validatePaymentCommand
//            = ValidatePaymentCommand
//            .builder()
//            .orderCode(event.getOrderCode())
//            .paymentCode(UUID.randomUUID().toString())
//            .userId(event.getUserId())
//            .payAmount(byOrderCode.getTotalDiscountedPrice())
//            .build();

          commandGateway.sendAndWait(inventoryQuantityUpdateCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            //Start the Compensating transaction
            cancelOrderCommand(event.getOrderCode());
        }

      
    }

    private void cancelOrderCommand(String orderCode) {
        CancelOrderCommand cancelOrderCommand
                = new CancelOrderCommand(orderCode);
        commandGateway.send(cancelOrderCommand);
    }
    
    @SagaEventHandler(associationProperty = "orderCode")
    private void handle(InventoryQuantityUpdateEvent event) {
        log.info("InventoryQuantityUpdateEvent in Saga for Order Id : {}",
                event.getOrderCode());
        try {
//
//            if(true)
//                throw new Exception();

          ValidatePaymentCommand validatePaymentCommand
          = ValidatePaymentCommand
          .builder()
          .orderCode(event.getOrderCode())
          .paymentCode(UUID.randomUUID().toString())
          .userId(this.userId)
          .payAmount(this.discountedPrice)
          .build();
            commandGateway.send(validatePaymentCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the compensating transaction
           cancelInventoryUpdate(event);
        }
     
    }
    
    private void cancelInventoryUpdate(InventoryQuantityUpdateEvent event) {
    	InventoryQuantityRevertCommand revertInventoryCommand
                = new InventoryQuantityRevertCommand(
                 event.getOrderCode (),event.getProducts()
        );

        commandGateway.sendAndWait(revertInventoryCommand);
    }
    
    @SagaEventHandler(associationProperty = "orderCode")
    private void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent in Saga for Order Id : {}",
                event.getOrderCode());


      CompleteOrderCommand completeOrderCommand
              = CompleteOrderCommand.builder()
              .orderCode(event.getOrderCode())
              .orderStatus("APPROVED")
              .build();

      commandGateway.send(completeOrderCommand);
   
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        CancelPaymentCommand cancelPaymentCommand
                = new CancelPaymentCommand(
                event.getPaymentCode(), event.getOrderCode ()
        );

        commandGateway.send(cancelPaymentCommand);
    }

//    @SagaEventHandler(associationProperty = "orderId")
//    public void handle(OrderShippedEvent event) {
//
//        log.info("OrderShippedEvent in Saga for Order Id : {}",
//                event.getOrderId());
//
//        CompleteOrderCommand completeOrderCommand
//                = CompleteOrderCommand.builder()
//                .orderId(event.getOrderId())
//                .orderStatus("APPROVED")
//                .build();
//
//        commandGateway.send(completeOrderCommand);
//    }

    @SagaEventHandler(associationProperty = "orderCode")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in Saga for Order Id : {}",
                event.getOrderCode());
    }

    @SagaEventHandler(associationProperty = "orderCode")
    @EndSaga
    public void handle(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent in Saga for Order Id : {}",
                event.getOrderCode());
    }

    @SagaEventHandler(associationProperty = "orderCode")
    public void handle(PaymentCancelledEvent event) {
        log.info("PaymentCancelledEvent in Saga for Order Id : {}",
                event.getOrderCode());
        
        cancelInventoryUpdate(new InventoryQuantityUpdateEvent(orderedProducts, event.getOrderCode()) );
    }
    @SagaEventHandler(associationProperty = "orderCode")
    public void handle(InventoryQuantityRevertEvent event) {
        log.info("PaymentCancelledEvent in Saga for Order Id : {}",
                event.getOrderCode());
        cancelOrderCommand(event.getOrderCode());
    }
}
