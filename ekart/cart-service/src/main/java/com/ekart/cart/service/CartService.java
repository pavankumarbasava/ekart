package com.ekart.cart.service;

import com.ekart.cart.dtos.AddressDto;
import com.ekart.cart.dtos.CartDto;
import com.ekart.cart.dtos.CartItemDto;
import com.ekart.cart.entity.CartItem;
import com.ekart.cart.exception.CartItemException;
import com.ekart.cart.exception.UserException;

public interface CartService {

	CartDto removeCartItem(String token, Long cartItemId) throws CartItemException, UserException;

	CartItem findCartItemById(Long cartItemId, Long userId) throws CartItemException;

//	CartItem createCartItem(CartItem cartItem);

///	CartDto updateCartItem(String token, Long cartid, CartItem cartItem) throws CartItemException, UserException;

	//CartDto addCartItem(String token, CartItemDto req) throws ProductException;

//	CartDto createCart(Long user);

//	CartDto findUserCart(String token);
//
//	CartDto updateCartItem(String token, Long cartid, CartItemDto cartItem) throws CartItemException, UserException;

	CartDto findUserCart(String token, String cartCode) throws Exception;

	CartDto addCartItem(String token, CartItemDto req, String cartCode) throws  Exception;

	CartDto updateCartItem(String token, Long cartid, CartItemDto cartItem) throws CartItemException, UserException, Exception;

//	String createCart(Long user, String token) throws Exception;

	String createCart(Long user) throws Exception;

	void emptyCart(String token, String cartCode) throws Exception;

	CartDto checkout(String token, AddressDto req, String cartCode) throws Exception;

}
