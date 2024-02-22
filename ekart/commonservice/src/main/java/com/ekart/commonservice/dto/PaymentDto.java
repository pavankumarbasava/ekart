package com.ekart.commonservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDto {


    private String cardholderName;

	private String cardNumber;
	
	private String cardType;
	
	

	private String expirationMonth;

	private String expirationYear;

	private String cvv;

}
