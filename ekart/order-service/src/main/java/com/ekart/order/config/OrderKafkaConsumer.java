//package com.ekart.order.config;
//
//
//
//import java.util.function.Consumer;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.ekart.commonservice.kafaka.events.StatusUpdateEvent;
//import com.ekart.order.service.impl.OrderServiceImpl;
//
//@Configuration
//public class OrderKafkaConsumer {
//
//	private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);
//	
//	@Bean
//    public Consumer<StatusUpdateEvent> updateOrderStatus(OrderServiceImpl orderService) {
//        return status ->
//            log.info("Updating Communication status for the account number : " + status.toString());
//            //accountsService.updateCommunicationStatus(accountNumber);
//        };
//    }
//
