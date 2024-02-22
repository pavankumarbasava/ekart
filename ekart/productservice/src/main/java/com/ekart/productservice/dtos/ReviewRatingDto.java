package com.ekart.productservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewRatingDto {
	
	private Long id;
	private String review;
	private Long userId;
	private Double rating;
	private String userName;
	

	
}

