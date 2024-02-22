package com.ekart.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

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

public class CartItemDto {

	private Long id;
	
	private Integer productId;

	 private String size;

    private Integer quantity;

	private Double price;

	private Double discountedPrice;
	
	private ProductDto product;
	


}
