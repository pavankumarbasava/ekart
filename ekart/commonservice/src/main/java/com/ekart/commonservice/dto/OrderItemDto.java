package com.ekart.commonservice.dto;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;

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
public class OrderItemDto {


	
	private Long id;
	
	
	private Order order;
	
	
	private Long product;
	

	private Integer quantity;
	

	private Integer price;
	

	private Integer discountedPrice;
	

	private LocalDateTime deliveryDate;
}
