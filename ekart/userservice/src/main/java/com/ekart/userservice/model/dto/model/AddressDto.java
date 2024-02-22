package com.ekart.userservice.model.dto.model;

import com.ekart.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressDto {

	
	
	private Long id;

	
	private String firstName;
	
	
	private String lastName;
	
	private String country;
	
	   private String streetAddressPart1;
	
    private String streetAddressPart2;
    
    private String landmark;

   
    private String city;

    private String state;

  
    private String zipCode;
    

    private String mobile;
    
    
    private boolean isDefaultAddress;
    

}
