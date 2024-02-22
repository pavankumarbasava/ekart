//package com.ekart.commonservice.mapper;
//
//import org.springframework.beans.BeanUtils;
//
//import com.ekart.cart.entity.Address;
//import com.ekart.cart.entity.Cart;
//import com.ekart.cart.entity.CartItem;
//import com.ekart.commonservice.dto.AddressDto;
//import com.ekart.commonservice.dto.CartDto;
//import com.ekart.commonservice.dto.CartItemDto;
//
//public interface CartMapper {
//
//	public static CartDto cartMap(Cart cart) {
//		
//
//
//		CartDto cartDto=new CartDto();
//		BeanUtils.copyProperties(cart, cartDto);
//		
//		if(cart.getAddress()!=null) {
//		AddressDto addressMap = addressMap(cart.getAddress());
//		cartDto.setAddress(addressMap);
//		}
//		
//		return cartDto;
//		
//	}
//	
//	public static CartItemDto cartItemMap(CartItem cartItem) {
//		CartItemDto cartItemDto=new CartItemDto();
//		BeanUtils.copyProperties(cartItem, cartItemDto);
//		
//		return cartItemDto;
//	}
//	
//	public static AddressDto addressMap(Address address) {
//		AddressDto addressDto=new AddressDto();
//		BeanUtils.copyProperties(address, addressDto);
//		
//		return addressDto;
//	}
//	public static Address addressMap(AddressDto addressDto) {
//		Address address=new Address();
//		BeanUtils.copyProperties(addressDto, address);
//		
//		return address;
//	}
//	
//}
