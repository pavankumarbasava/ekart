package com.ekart.order.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.order.command.api.command.CreateOrderCommand;
import com.ekart.order.request.OrderRequest;
import com.ekart.order.response.ResponseMessage;
import com.ekart.order.service.impl.OrderServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

	  private transient CommandGateway commandGateway;
	  
////		@Autowired
//		private  OrderServiceImpl orderService;
	  
	  @PostMapping
	    public ResponseEntity<?> createOrder(@RequestBody  OrderRequest orderRequest, @RequestHeader("Authorization") String token) {

		  // Order buildOrder;
		    String aggregateId=null;
			try {
				 // buildOrder = orderService.buildOrder(orderDto.getCartCode(),token);
				  CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
						  .orderCode(UUID.randomUUID().toString())
						  .cartCode(orderRequest.getCartCode())
						  .orderStatus("CREATED")
						  .token(token)
						  .payment(orderRequest.getPayment())
						  .userId(null).build();
				  
				  Object sendAndWait = commandGateway.sendAndWait(createOrderCommand);
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("There is an error while saving order " ,e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ResponseMessage(e.getMessage()));
				
			}

          
	  
	  	return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(aggregateId );
	    
	    }
		
//		@PostMapping("/create")
//		public Mono<ResponseEntity<?>> createOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token) {
////			return userService.login(signInForm).map(ResponseEntity::ok).onErrorResume(error -> {
////				JwtResponseMessage errorjwtResponseMessage = new JwtResponseMessage(null, null
////                  
////				);
//			   Order buildOrder;
//		    String aggregateId=null;
//			try {
//			 orderService.buildOrder(orderRequest.getCartCode(),token);
//				  
//				  
//			
//				  
//				  
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				log.error("There is an error while saving order " ,e);
//				return Mono.just( ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//						.body(new ResponseMessage(e.getMessage())));
//				
//			}
//			
//			
//				return Mono.just(new ResponseEntity<>(new ResponseMessage("There is an error"), HttpStatus.UNAUTHORIZED));
////			});
//		}

	
}
