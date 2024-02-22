package com.ekart.order.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class CartDto {

 
   

    private String cartCode;

    
    @JsonProperty("entries")
    private Set<CartItemDto> cartItems;

    
    private Double totalPrice;
    
   
    private Double totalItem;
    
    
    private Double totalDiscountedPrice;
    

    private Double discounte;
    
    private AddressDto address;
    
    
    
  
	
	
}
