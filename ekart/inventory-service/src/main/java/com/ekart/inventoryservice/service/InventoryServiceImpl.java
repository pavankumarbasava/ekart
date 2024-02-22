package com.ekart.inventoryservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.ekart.commonservice.kafaka.events.StatusUpdateEvent;

@Service
public class InventoryServiceImpl implements InventoryService {

	private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
	
	
	@Autowired
	  private  StreamBridge streamBridge;
	
	public String test() {
	//	sendCommunication();
		return "pavan";
	}
	
	
//	 private void sendCommunication() {
//		StatusUpdateEvent statusUpdateEvent = new  StatusUpdateEvent(null,"updated",null);
//        log.info("Sending Communication request for the details: {}", statusUpdateEvent.toString());
//        var result = streamBridge.send("sendCommunication-out-0", statusUpdateEvent);
//        log.info("Is the Communication request successfully triggered ? : {}", result);
//    }
}
