//package com.ekart.inventoryservice.command.api.aggregate;
//
//import java.util.List;
//
//import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.eventsourcing.EventSourcingHandler;
//import org.axonframework.modelling.command.AggregateIdentifier;
//import org.axonframework.modelling.command.AggregateLifecycle;
//import org.axonframework.spring.stereotype.Aggregate;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.ekart.commonservice.commands.InventoryQuantityRevertCommand;
//import com.ekart.commonservice.commands.InventoryQuantityUpdateCommand;
//import com.ekart.commonservice.dto.ProductSagaDto;
//import com.ekart.commonservice.events.InventoryQuantityRevertEvent;
//import com.ekart.commonservice.events.InventoryQuantityUpdateEvent;
//import com.ekart.inventoryservice.entity.Product;
//import com.ekart.inventoryservice.repository.InventoryRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Aggregate
//@Slf4j
//public class InventoryAggregate {
//
//	
//	@AggregateIdentifier
//	private String orderCode;
//	private List<ProductSagaDto> products;
//	
//	@Autowired
//	private InventoryRepository inventoryRepository;
//	
//	@CommandHandler
//	public  InventoryAggregate(InventoryQuantityUpdateCommand command) throws Exception {
//	
//		if(command.getProducts().size()==0){
//			throw new Exception("products size is empty");
//		}
//		
//		 for (ProductSagaDto ProductSagaDto : command.getProducts()) {
//			  Product product = inventoryRepository.findById(   ProductSagaDto.getId().intValue()).get();
//			 if( product.getQuantity()<ProductSagaDto.getQuantity() ) {
//				 log.error("This :{} product is out of stock for order:{}", ProductSagaDto.getId(),command.getOrderCode());
//				 throw new Exception(String.format("This %s this product is out of stock for order:{}",
//						 ProductSagaDto.getId(),command.getOrderCode()));
//				 
//			 }else {
//				 
//				 product.setQuantity(product.getQuantity()-ProductSagaDto.getQuantity()); 
//				 inventoryRepository.save(product);
//			 }
//			 
//		}
//	      
//	       
//		
//		InventoryQuantityUpdateEvent inventoryQuantityUpdateEvent = new InventoryQuantityUpdateEvent(products,command.getOrderCode());		
//		inventoryQuantityUpdateEvent.setProducts(command.getProducts());
//	    
//		 AggregateLifecycle.apply(inventoryQuantityUpdateEvent);
//	
//	}
//	
//	@EventSourcingHandler
//	public void on(InventoryQuantityUpdateEvent event) {
//		this.products=event.getProducts();
//	}
//	
//	@CommandHandler
//	public void InventoryRevertAggregate(InventoryQuantityRevertCommand command) throws Exception {
//	
//		if(command.getProducts().size()==0){
//			throw new Exception("products size is empty");
//		}
//		
//		 for (ProductSagaDto ProductSagaDto : command.getProducts()) {
//			  Product product = inventoryRepository.findById(   ProductSagaDto.getId().intValue()).get();
//			 
//				 
//				 product.setQuantity(product.getQuantity()+ProductSagaDto.getQuantity()); 
//				 inventoryRepository.save(product);
//			 
//			 
//		}
//	      
//	       
//		
//		InventoryQuantityRevertEvent inventoryQuantityRevertEvent = new InventoryQuantityRevertEvent(products,command.getOrderCode());		
//		inventoryQuantityRevertEvent.setProducts(command.getProducts());
//	
//		 AggregateLifecycle.apply(inventoryQuantityRevertEvent);
//	}
//	
//	@EventSourcingHandler
//	public void on(InventoryQuantityRevertCommand event) {
//		this.products=event.getProducts();
//	}
//}
