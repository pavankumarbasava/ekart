//package com.ekart.productservice.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.ekart.productservice.entity.Rating;
//
//public interface RatingRepository extends JpaRepository<Rating, Long> {
//
//	
//	@Query("select r from Rating r where r.product.id=:productId")
//	List<Rating> getAllProductsRating(@Param("productId") Integer productId);
//
//	@Query("select r from Rating r where r.product.id=:productId and r.userId=:userId")
//	Rating getUserReviewAndProduct(@Param("productId") Integer productId,@Param("userId") Long userId);
//
//}
