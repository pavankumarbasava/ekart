
package com.ekart.order.command.api.events;

import javax.transaction.Transactional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ekart.commonservice.events.OrderCancelledEvent;
import com.ekart.commonservice.events.OrderCompletedEvent;
import com.ekart.order.entity.Order;
import com.ekart.order.entity.OrderStatus;
import com.ekart.order.repository.OrderRepository;
import com.ekart.order.service.OrderService;
import com.ekart.order.service.impl.OrderServiceImpl;

@Component
@ProcessingGroup("order")
public class OrderEventsHandler {

	@Autowired
    private OrderRepository orderRepository;

//	@Autowired
//	private  OrderServiceImpl orderService;
	

    public void orderCreateHandler(OrderCreatedEvent event) throws Exception {
//      Order order = event.getOrder();
    
   //   throw new Exception("Exception Occurred");
    //  Order  buildOrder = orderService.buildOrder(event.getCartCode(),event.getBearerToken());
 	 try {
 		Order order = event.getOrder();
 		 orderRepository.save(order);
	} catch (Exception e) {
		event.setOrderStatus("ERROR");
		event.setErrorContext("There is an error while creating order "+event.getOrderCode());
	}
    	
   
//  .userId(buildOrder.getUser())
//    .order(buildOrder)
//        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order
                = orderRepository.findByOrderCode(event.getOrderCode());

        order.setOrderStatus(OrderStatus.valueOf(event.getOrderStatus()) );

        orderRepository.save(order);
    }

    @Transactional
    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order
                = orderRepository.findByOrderCode(event.getOrderCode());

        //order.setOrderStatus(OrderStatus.valueOf(event.getOrderStatus()) );
        if(order!=null) {
        orderRepository.delete(order);
        }
    }
}
