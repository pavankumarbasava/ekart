package com.ekart.productservice.service;

import java.util.List;

import com.ekart.productservice.dtos.ReviewRatingDto;
import com.ekart.productservice.dtos.WishListDto;
import com.ekart.productservice.entity.ReviewRating;
import com.ekart.productservice.entity.WishList;
import com.ekart.productservice.requests.ReviewRatingRequest;

public interface ReviewRatingAndWishListService {

	


	ReviewRating createReviewRating(ReviewRatingRequest req, String user, String token) throws Exception;


	List<ReviewRatingDto> getAllReviewRatings(Integer productId);

	WishListDto deleteWishList(Long productId, String userName, String token) throws Exception;

	WishList saveWishList(Long product, String userName, String token) throws Exception;

	WishListDto findByUserId(String userName, String token) throws Exception;







}
