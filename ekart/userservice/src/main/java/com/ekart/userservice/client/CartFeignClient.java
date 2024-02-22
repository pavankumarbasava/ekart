package com.ekart.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ORDER-SERVICE",
contextId = "UserFeignClient",
path = "/api/cart",
decode404 = true
)
public interface CartFeignClient {
	
	@PostMapping("/create/{userId}")
	public  void createCart(@PathVariable Long userId);
}
