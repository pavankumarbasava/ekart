package com.ekart.inventoryservice.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ekart.commonservice.dto.UserDto;



@FeignClient(name = "USER-SERVICE",
contextId = "UserFeignClient",
path = "/api/information",
decode404 = true
)
public interface UserFeignClient {
	
    @GetMapping(value = "/user/username")
	 ResponseEntity<UserDto> getUserByUsername(@RequestParam("name") String username,
			 @RequestHeader("Authorization") String token);
}
