package com.ekart.userservice.model.dto.model;

import java.util.ArrayList;
import java.util.List;

import com.ekart.userservice.entity.Address;
import com.ekart.userservice.entity.PaymentInformation;
import com.ekart.userservice.entity.Role;

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

    
    private List<Role> roles = new ArrayList<>();
    
    
    
   
    private List<Address> addresses;

    private List<PaymentInformation> paymentInformation;

  
    private String gender;
    
}
