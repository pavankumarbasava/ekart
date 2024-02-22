package com.ekart.commonservice.dto;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String productTitle;
    private String description;
    private String brand;
    private String color;
    private String imageUrl;
    private String sku;
    private Double price;
    private Integer quantity;
    private Double discountedPrice;
    private Double discountPersent; 
    private Boolean stock;
   

    @JsonProperty("category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto categoryDto;
    
    @JsonProperty("size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Size> sizes;

}
