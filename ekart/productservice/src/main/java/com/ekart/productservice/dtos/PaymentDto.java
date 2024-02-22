package com.ekart.productservice.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDto {

	private Long id;

	

	private String cardholderName;

	
	private String cardNumber;

	
	private LocalDate expirationDate;


	private String cvv;


}
