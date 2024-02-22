package com.ekart.order.dtos;

import java.util.ArrayList;
import java.util.List;

import com.ekart.commonservice.dto.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {


 
    private Long id;

    private String name;


    private String username;

    private String email;

 
    private String avatar;


    
    
    
   
    private List<AddressDto> addresses;

    private List<PaymentDto> paymentInformation;

  
    private String gender;
    
}
