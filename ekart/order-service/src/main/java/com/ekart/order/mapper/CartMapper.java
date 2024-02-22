//package com.ekart.order.mapper;
//
//import org.springframework.beans.BeanUtils;
//
//import com.ekart.order.dtos.CartDto;
//import com.ekart.order.dtos.CartItemDto;
//import com.ekart.order.entity.Cart;
//import com.ekart.order.entity.CartItem;
//
//public interface CartMapper {
//
//	public static CartDto cartMap(Cart cart) {
//		
////		private Long productId;
////
////
////	    private Integer quantity;
////
////		private Integer price;
////
////		private Integer discountedPrice;
////		
////		private ProductDto product;
////		Set<CartItem> cartItems = cart.getCartItems();
////		if( cartItems.size()!=0) {
////		cartItems.stream().map((CartItem cartItem)->{
////			
////			 CartItemDto.builder()
////			 .quantity(cartItem.getQuantity())
////		})
////	   
////		}
////		
////		
////		
//////		
//////		
//////		   CartItemDto.builder()
//////		   .quantity(cartItem.get)
//////	   }
////	   
////		
////		return CartDto.builder()
////				.cartCode(cart.getCartCode())
////				.discounte(cart.getDiscounte())
////				.totalPrice(cart.getTotalPrice())
////				.totalItem(cart.getTotalItem())
////				.totalDiscountedPrice(cart.getTotalDiscountedPrice()).build();
//		
//
//		CartDto cartDto=new CartDto();
//		BeanUtils.copyProperties(cart, cartDto);
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
//	
//}
