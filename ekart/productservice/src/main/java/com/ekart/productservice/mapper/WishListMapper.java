package com.ekart.productservice.mapper;



import org.springframework.util.ObjectUtils;

import com.ekart.productservice.dtos.WishListDto;
import com.ekart.productservice.entity.WishList;

public interface WishListMapper {

	public static WishListDto map(WishList wishlist) {
		
		return WishListDto.builder().userId(wishlist.getUserId()).build();
				
				
	}
	
	
	
}
