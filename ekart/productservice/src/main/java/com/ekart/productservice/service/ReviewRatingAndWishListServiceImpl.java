package com.ekart.productservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.dtos.ReviewRatingDto;
import com.ekart.productservice.dtos.UserDto;
import com.ekart.productservice.dtos.WishListDto;
import com.ekart.productservice.entity.Product;
import com.ekart.productservice.entity.ReviewRating;
import com.ekart.productservice.entity.WishList;
import com.ekart.productservice.mapper.ProductMappingHelper;
import com.ekart.productservice.mapper.ReviewRattingMapper;
import com.ekart.productservice.mapper.WishListMapper;
import com.ekart.productservice.repository.ProductRepository;
import com.ekart.productservice.repository.ReviewRatingRepository;
import com.ekart.productservice.repository.WishListRepo;
import com.ekart.productservice.requests.ReviewRatingRequest;
import com.ekart.productservice.service.clients.UserFeignClient;

@Service

public class ReviewRatingAndWishListServiceImpl implements ReviewRatingAndWishListService{

	@Autowired
	private ReviewRatingRepository reviewRatingRepository;
//	@Autowired
//	private RatingRepository ratingRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserFeignClient userFeignClient;
	@Autowired
	private  WishListRepo wishListRepo;
//    private final RestTemplate restTemplate;
//    private final ModelMapper modelMapper;
	
	private static final Logger logger= LoggerFactory.getLogger(ReviewRatingAndWishListServiceImpl.class);
	

@Transactional
	@Override
	public WishListDto deleteWishList(Long productId, String userName,String token) throws Exception{
		UserDto body = null;
		try {
			body = userFeignClient.getUserByUsername(userName, token).getBody();

		} catch (Exception e) {
			logger.error("Error while fetching user feign client datat ", e);
			throw e;
		}
		wishListRepo.deleteWishList(productId,body.getId());
		
		 List<Long> wishListByUserId = wishListRepo.findWishListByUserId(body.getId());
		 List<ProductDto> list = wishListByUserId.stream().map((Long prodId)->{
				return ProductMappingHelper.map( productRepository.findById(prodId.intValue()).get());
			 
		 }).distinct().toList();
		 return WishListDto.builder().userId(body.getId()).productDto(list).build();
	}
	
	@Override
	public WishList saveWishList(Long product, String userName, String token) throws Exception{
		UserDto body = null;
		WishList wishList=null;
		try {
			body = userFeignClient.getUserByUsername(userName, token).getBody();
			wishList = wishListRepo.findByuserNameProductId(product,body.getId());
		} catch (Exception e) {
			logger.error("Error while fetching user feign client datat ", e);
			throw e;
		}
        if(wishList!=null &&  wishList.getId()!=null) {
        	throw new Exception("Wish list alredy added");
        }
		
	    wishList = new WishList();

		wishList.setProducts(product);
		wishList.setUserId(body.getId());
		wishList.setLikeDate(LocalDateTime.now());
		return wishListRepo.save(wishList);
	}
    
    

	@Override
	public WishListDto findByUserId(String userName,String token) throws Exception {
		UserDto body = null;
		try {
			body = userFeignClient.getUserByUsername(userName, token).getBody();

		} catch (Exception e) {
			logger.error("Error while fetching user feign client dat ", e);
			throw e;
		}
		 List<Long> wishListByUserId = wishListRepo.findWishListByUserId(body.getId());
		 List<ProductDto> list = wishListByUserId.stream().map((Long prodId)->{
				return ProductMappingHelper.map( productRepository.findById(prodId.intValue()).get());
			 
		 }).distinct().toList();
		 
		 return WishListDto.builder().userId(body.getId()).productDto(list).build();
		
		 


	}
    

	@Override
	public ReviewRating createReviewRating(ReviewRatingRequest req,String user,String token) throws Exception {
		Product product=null;
		 UserDto body=null;
		 ReviewRating reviewRating ;
		try {
			 product=productRepository.findById(req.getProductId()).get();
			  body = userFeignClient.getUserByUsername(user,token).getBody();
			  reviewRating = reviewRatingRepository.getUserReviewAndProduct(req.getProductId(), body.getId());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("There is an error while fetching product and user data in reviews",e);
			throw new Exception("Product or User not found with this id ",e);
		}
		if(product.getId()==null||body.getId()==null) {
			return new ReviewRating();
		}
		if( reviewRating!=null&&reviewRating.getId()!=null) {
			if (req.getReview() != null) {
				reviewRating.setReview(req.getReview());
				reviewRating.setReviewUpdatedAt(LocalDateTime.now());
			}
			if (req.getRating() != null) {
				reviewRating.setRating(req.getRating());
				reviewRating.setRatingUpdatedAt(LocalDateTime.now());
			}
			return reviewRatingRepository.save(reviewRating);
		}
		reviewRating=new ReviewRating();
		reviewRating.setUserId(body.getId());
		if (req.getReview() != null) {
		reviewRating.setReview(req.getReview());
		reviewRating.setReviewCreatedAt(LocalDateTime.now());
		}
		if (req.getRating() != null) {
			reviewRating.setRating(req.getRating());
			reviewRating.setRatingCreatedAt(LocalDateTime.now());
		}
		reviewRating.setUserName(body.getName());
		
		
		//product.getReviews().add(review);
		//productRepository.save(product);
		
		reviewRating.setProduct(product);
		
		return reviewRatingRepository.save(reviewRating);
		
	}

	@Override
	public List<ReviewRatingDto> getAllReviewRatings(Integer productId) {
		
		return reviewRatingRepository.getAllProductsReviewRatings(productId)
				.stream().map(ReviewRattingMapper::mapper).distinct().toList();
	}

	
   




	
}
