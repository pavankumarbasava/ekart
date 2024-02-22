package com.ekart.productservice.requests;


public class ReviewRatingRequest {
	
	private Integer productId;
	private String review;
	private Double rating;
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

	
}
