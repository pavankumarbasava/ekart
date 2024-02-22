package com.ekart.cart.mapper;

import org.springframework.beans.BeanUtils;

import com.ekart.cart.dtos.AddressDto;
import com.ekart.cart.dtos.CartDto;
import com.ekart.cart.dtos.CartItemDto;
import com.ekart.cart.entity.Address;
import com.ekart.cart.entity.Cart;
import com.ekart.cart.entity.CartItem;

public interface CartMapper {

	public static CartDto cartMap(Cart cart) {
		
//		private Long productId;
//
//
//	    private Integer quantity;
//
//		private Integer price;
//
//		private Integer discountedPrice;
//		
//		private ProductDto product;
//		Set<CartItem> cartItems = cart.getCartItems();
//		if( cartItems.size()!=0) {
//		cartItems.stream().map((CartItem cartItem)->{
//			
//			 CartItemDto.builder()
//			 .quantity(cartItem.getQuantity())
//		})
//	   
//		}
//		
//		
//		
////		
////		
////		   CartItemDto.builder()
////		   .quantity(cartItem.get)
////	   }
//	   
//		
//		return CartDto.builder()
//				.cartCode(cart.getCartCode())
//				.discounte(cart.getDiscounte())
//				.totalPrice(cart.getTotalPrice())
//				.totalItem(cart.getTotalItem())
//				.totalDiscountedPrice(cart.getTotalDiscountedPrice()).build();
		

		CartDto cartDto=new CartDto();
		BeanUtils.copyProperties(cart, cartDto);
		
		if(cart.getAddress()!=null) {
		AddressDto addressMap = addressMap(cart.getAddress());
		cartDto.setAddress(addressMap);
		}
		
		return cartDto;
		
	}
	
	public static CartItemDto cartItemMap(CartItem cartItem) {
		CartItemDto cartItemDto=new CartItemDto();
		BeanUtils.copyProperties(cartItem, cartItemDto);
		
		return cartItemDto;
	}
	
	public static AddressDto addressMap(Address address) {
		AddressDto addressDto=new AddressDto();
		BeanUtils.copyProperties(address, addressDto);
		
		return addressDto;
	}
	public static Address addressMap(AddressDto addressDto) {
		Address address=new Address();
		BeanUtils.copyProperties(addressDto, address);
		
		return address;
	}
	
}
