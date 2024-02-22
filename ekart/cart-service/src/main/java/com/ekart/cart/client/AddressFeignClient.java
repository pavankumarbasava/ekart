package com.ekart.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ekart.cart.dtos.AddressDto;
import com.ekart.cart.entity.Address;

@FeignClient(name = "USER-SERVICE",
contextId = "AddressFeignClient",
path = "/api/information/address",
decode404 = true
)
public interface AddressFeignClient {
	
	@PostMapping(value = "/save")
	public ResponseEntity<Address> saveAddress(@RequestBody  AddressDto req,  @RequestHeader("Authorization") String token);
}
