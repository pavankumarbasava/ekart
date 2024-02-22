package com.ekart.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ekart.commonservice.dto.CartDto;
import com.ekart.order.exception.UserException;

@FeignClient(name = "CART-SERVICE",
contextId = "CartFeignClient",
path = "/api/cart",
decode404 = true
)
public interface CartFeignClient {

	@GetMapping("/get/{cartCode}")
	public ResponseEntity<CartDto> findUserCartHandler(@PathVariable String cartCode ,@RequestHeader("Authorization") String token) throws UserException;
		
}
