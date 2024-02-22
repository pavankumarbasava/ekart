package com.ekart.inventoryservice.config;


import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ekart.commonservice.kafaka.events.CommonEvent;
import com.ekart.commonservice.kafaka.events.StatusUpdateEvent;
import com.ekart.inventoryservice.service.InventoryServiceImpl;

@Configuration
public class InventoryKafkaConsumer {

	@Autowired
	private InventoryServiceImpl inventoryServiceImpl;
	
	private static final Logger log = LoggerFactory.getLogger(InventoryKafkaConsumer.class);
	
	   @Bean
	    public Function<CommonEvent,StatusUpdateEvent> updateInventory() {
	        return accountNumber -> {
	            log.info("Updating updateInventory status for the account number : " + inventoryServiceImpl.test());
	            StatusUpdateEvent statusUpdateEvent = new  StatusUpdateEvent(null,"updated",null);  
	            return statusUpdateEvent;
	            //  inventoryServiceImpl.updateCommunicationStatus(accountNumber);
	        };
	    }
	
}
