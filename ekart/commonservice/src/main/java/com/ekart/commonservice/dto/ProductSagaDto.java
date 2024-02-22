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
public class ProductSagaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
  
    private Integer quantity;

   

}
