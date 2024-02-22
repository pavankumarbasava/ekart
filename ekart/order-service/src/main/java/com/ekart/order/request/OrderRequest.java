package com.ekart.order.request;

import com.ekart.commonservice.dto.PaymentDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class OrderRequest {

	
	  private String cartCode;
	  private PaymentDto payment;
}
