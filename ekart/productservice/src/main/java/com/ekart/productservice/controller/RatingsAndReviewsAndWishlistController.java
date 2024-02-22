package com.ekart.productservice.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.productservice.dtos.ReviewRatingDto;
import com.ekart.productservice.dtos.WishListDto;
import com.ekart.productservice.entity.ReviewRating;
import com.ekart.productservice.entity.WishList;
import com.ekart.productservice.mapper.ReviewRattingMapper;
import com.ekart.productservice.mapper.WishListMapper;
import com.ekart.productservice.requests.ReviewRatingRequest;
import com.ekart.productservice.response.ResponseMessage;
import com.ekart.productservice.service.ReviewRatingAndWishListService;

@RestController
@RequestMapping("/api/product/feedback")
public class RatingsAndReviewsAndWishlistController {

	
	private static final Logger logger= LoggerFactory.getLogger(RatingsAndReviewsAndWishlistController.class);
	

	
	@Autowired
	private ReviewRatingAndWishListService reviewRatingService;
	
	@PostMapping("/reviewRating/create")
	public ResponseEntity<ReviewRatingDto> createReviewHandler(@RequestBody ReviewRatingRequest req,
			@RequestHeader("User") String user,@RequestHeader("Authorization") String token) throws Exception{
		
		System.out.println("product id "+req.getProductId()+" - "+req.getReview()+" token "+token);
		ReviewRating reviewRating=reviewRatingService.createReviewRating(req, user,token);
		System.out.println("product review "+req.getReview());
		return new ResponseEntity<ReviewRatingDto>(ReviewRattingMapper.mapper(reviewRating),HttpStatus.ACCEPTED);
	}
//	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/reviewRating/product/{productId}")
	public ResponseEntity<List<ReviewRatingDto>> getProductsReviewHandler(@PathVariable Integer productId){
		List<com.ekart.productservice.dtos.ReviewRatingDto>reviews=reviewRatingService.getAllReviewRatings(productId);
		return new ResponseEntity<List<ReviewRatingDto>>(reviews,HttpStatus.OK);
	}
	

	
	
	
//	
	  

	    @GetMapping("/wishlist/getWishList")
	    public ResponseEntity<?> findById(@RequestHeader("User")  String userName,
	    		@RequestHeader("Authorization") String token ) {
	    	WishListDto byUserId=null;
	    	try {
	    		 byUserId = this.reviewRatingService.findByUserId(userName,token);
			} catch (Exception e) {
				logger.error("There is an issue while getting wishlist ",e);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("this product is alredy wishlisted"));
			    
			}
	        return ResponseEntity.ok(byUserId);
	    }

	   

	    @PostMapping("/wishlist/save/{productId}")
	    public ResponseEntity<?> save(@PathVariable
	                                             @NotNull(message = "Input must not be NULL")
	                                             String productId, @RequestHeader("User") String userName,
	                                             @RequestHeader("Authorization") String token) {
	    	WishList saveWishList=null;
	    	try {
	    		saveWishList = this.reviewRatingService.saveWishList(Long.valueOf(productId),userName,token);
		    	
			} catch (Exception e) {
				logger.error("There is an issue while saving wisshlist ",e);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("this product is alredy wishlisted"));
			    
			} 
	    	
	    	return ResponseEntity.ok( WishListMapper.map(saveWishList)  );
	    }


	    @DeleteMapping("/wishlist/delete/{productId}")
	    public ResponseEntity<WishListDto> delete(@PathVariable
	                                             @NotNull(message = "Input must not be NULL")
	                                             String productId, @RequestHeader("User") String userName,
	                                             @RequestHeader("Authorization") String token) throws Exception {
	    	WishListDto remainingWishList=null;
	    	
	    	try {
	    		 remainingWishList = this.reviewRatingService.deleteWishList(Long.valueOf(productId),userName,token);
			} catch (Exception e) {
				logger.error("There is an issue while deleting wishlisted product ",e);
				throw new Exception(e);
			}
	        return ResponseEntity.ok( remainingWishList);
	    }

	  

	    
	
}
