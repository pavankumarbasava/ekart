package com.ekart.productservice.mapper;

import com.ekart.productservice.dtos.ReviewRatingDto;
import com.ekart.productservice.entity.ReviewRating;

public interface ReviewRattingMapper {

	
	public static ReviewRatingDto mapper(final ReviewRating reviewRating) {
        return ReviewRatingDto.builder()
                .id(reviewRating.getId())
                .review(reviewRating.getReview())
                .userId(reviewRating.getUserId())
                .rating(reviewRating.getRating())
                .userName(reviewRating.getUserName())
                .build();
                
             
    }
	public static ReviewRating mapper(final ReviewRatingDto reviewRatingDto) {
        return ReviewRating.builder()
                .id(reviewRatingDto.getId())
                .rating(reviewRatingDto.getRating())
                .userId(reviewRatingDto.getUserId())
                .review(reviewRatingDto.getReview())
                .userName(reviewRatingDto.getUserName())
                .build();
                
             
    }
    
}
