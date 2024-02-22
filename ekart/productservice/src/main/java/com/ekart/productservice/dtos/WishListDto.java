package com.ekart.productservice.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WishListDto implements Serializable {

    
    private static final long serialVersionUID = 1L;

        
    private Long userId;

 

    

    @JsonProperty(value = "products")
    @JsonInclude(Include.NON_NULL)
    private List<ProductDto> productDto;

}