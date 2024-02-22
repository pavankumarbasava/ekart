package com.ekart.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.cart.dtos.AddressDto;
import com.ekart.cart.dtos.CartDto;
import com.ekart.cart.dtos.CartItemDto;
import com.ekart.cart.exception.CartItemException;
import com.ekart.cart.exception.ProductException;
import com.ekart.cart.exception.UserException;
import com.ekart.cart.response.ResponseMessage;
import com.ekart.cart.service.CartService;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

	@Autowired
	private CartService  cartService;
	


	@DeleteMapping("/cartItem/remove/{cartItemId}")
	public ResponseEntity<?> deleteCartItemHandler(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String token) throws CartItemException, UserException {
		CartDto cart=null;
		try {
			 cart = cartService.removeCartItem(token, cartItemId);
		} catch (Exception e) {
			log.error("there is an error while removing the cart item from  cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));

		}

		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(cart);
	}
	
	@PutMapping("/cartItem/{cartItemId}")
	public ResponseEntity<?>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItemDto cartItem, @RequestHeader("Authorization") String token) throws CartItemException, UserException{
		
		CartDto cart=null;
		try {
			cart =cartService.updateCartItem( token,cartItemId, cartItem);
		} catch (Exception e) {
			log.error("there is an error while removing the cart item from  cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));
		}
	
		
		
		return new ResponseEntity<>(cart,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/get/{cartCode}")
	public ResponseEntity<?> findUserCartHandler(@PathVariable String cartCode ,@RequestHeader("Authorization") String token) throws UserException{
		
		CartDto cart=null; 
		try {
			cart=cartService.findUserCart(token,cartCode);
	
		} catch (Exception e) {
			log.error("there is an error while getting cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));
		}
		
		
		
		return new ResponseEntity<CartDto>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add/{cartCode}")
	public ResponseEntity<?> addItemToCart(@PathVariable String cartCode,@RequestBody CartItemDto req, @RequestHeader("Authorization") String token) throws UserException, ProductException{
		CartDto cart=null; 
		try {
			cart= cartService.addCartItem(token, req,cartCode);
	
		} catch (Exception e) {
			log.error("there is an error while removing the cart item from  cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));
		}
		
	
		
		return new ResponseEntity<CartDto>(cart,HttpStatus.ACCEPTED);
		
	}
	
	@PostMapping("/create/{userId}")
	public String createCart(@PathVariable Long userId) throws Exception{
		 String cartCode=null;
		try {
		  cartCode = cartService.createCart(userId);
	
		} catch (Exception e) {
			log.error("there is an error while creating cart ", e);
			throw new Exception(e.getMessage());
		}
		return cartCode;
		
	}
	@DeleteMapping("/empty/{cartCode}")
	public ResponseEntity<?> emptyCart(@PathVariable String cartCode, @RequestHeader("Authorization") String token) throws UserException, ProductException{

		try {
		cartService.emptyCart(token,cartCode);
	
		} catch (Exception e) {
			log.error("there is an error while emptying  cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));
		}
		
	
		
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Cart is empty now"),HttpStatus.ACCEPTED);
		
	}
	
	@PostMapping("/{cartCode}/checkout")
	public ResponseEntity<?> checkout(@PathVariable String cartCode,@RequestBody AddressDto req, @RequestHeader("Authorization") String token) throws UserException, ProductException{
		CartDto cart=null; 
		try {
			cart= cartService.checkout(token, req,cartCode);
	
		} catch (Exception e) {
			log.error("there is an error while removing the cart item from  cart ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseMessage(e.getMessage()));
		}
		
	
		
		return new ResponseEntity<CartDto>(cart,HttpStatus.ACCEPTED);
		
	}
	
	
}
