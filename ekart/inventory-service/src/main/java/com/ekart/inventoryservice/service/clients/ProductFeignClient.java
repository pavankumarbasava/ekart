package com.ekart.inventoryservice.service.clients;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ekart.commonservice.dto.ProductDto;



@FeignClient(name = "PRODUCT-SERVICE",
contextId = "ProductFeignClient",
path = "/api/product",
decode404 = true
)
public interface ProductFeignClient {
	

    @GetMapping("/getById")
    public ResponseEntity<ProductDto> findById(@RequestParam("productId")
    
                                               @NotBlank(message = "Input must not be blank!")
                                               @Valid  Integer productId);
}
