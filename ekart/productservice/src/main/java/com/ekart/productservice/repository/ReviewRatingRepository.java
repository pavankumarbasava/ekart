package com.ekart.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.productservice.entity.ReviewRating;

public interface ReviewRatingRepository extends JpaRepository<ReviewRating, Long> {

	
	@Query("select r from ReviewRating r where r.product.id=:productId")
	List<ReviewRating> getAllProductsReviewRatings(@Param("productId") Integer productId);
	
	@Query("select r from ReviewRating r where r.product.id=:productId and r.userId=:userId")
	ReviewRating getUserReviewAndProduct(@Param("productId") Integer productId,@Param("userId") Long userId);
	
}
