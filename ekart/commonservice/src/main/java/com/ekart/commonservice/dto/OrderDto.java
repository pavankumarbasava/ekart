package com.ekart.commonservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderDto {

	
	    private Long id;

	  
	    private List<OrderItemDto> orderItems = new ArrayList<>();

	    private LocalDateTime orderDate;

	    private LocalDateTime deliveryDate;

	    

	    private Double totalPrice;
	    
	    private Integer totalDiscountedPrice;
	    
	    private Integer discounte;

	    private OrderStatus orderStatus;
	    
	    private Integer totalItem;
	    
	    private AddressDto shippingAddress;
	    
	    private Long paymentId;
	    

	    private Long user;
	    

	    private String orderCode;
	    
	    private String cartCode;
	    
}
