package com.ekart.cart.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ekart.cart.client.AddressFeignClient;
import com.ekart.cart.client.ProductFeignClient;
import com.ekart.cart.client.UserFeignClient;
import com.ekart.cart.dtos.AddressDto;
import com.ekart.cart.dtos.CartDto;
import com.ekart.cart.dtos.CartItemDto;
import com.ekart.cart.dtos.ProductDto;
import com.ekart.cart.dtos.UserDto;
import com.ekart.cart.entity.Address;
import com.ekart.cart.entity.Cart;
import com.ekart.cart.entity.CartItem;
import com.ekart.cart.exception.CartItemException;
import com.ekart.cart.exception.UserException;
import com.ekart.cart.mapper.CartMapper;
import com.ekart.cart.repository.CartItemRepository;
import com.ekart.cart.repository.CartRepository;
import com.ekart.cart.service.CartService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	
	

	
	private UserFeignClient userClientService;

	
	private ProductFeignClient productClientService;

	private CartItemRepository cartItemRepository;

	private CartRepository cartRepo;

	private AddressFeignClient addressFeignClient;
	 

	@Override
	public CartDto removeCartItem(String token, Long cartItemId) throws CartItemException, UserException {

		
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		
		System.out.println("userId- " + userFromToken.getId() + " cartItemId " + cartItemId);

		CartItem cartItem = findCartItemById(cartItemId, userFromToken.getId());

		if(cartItem==null || cartItem.getId()==null) {
			throw new CartItemException("CartItem not found");
		}
		if(userFromToken.getId()!=cartItem.getUserId()) {
			throw new CartItemException("Not an authenticated user");
		}
		cartItemRepository.deleteById(cartItem.getId());
		return updateCartDetails(cartRepo.findByUserId(userFromToken.getId()));
	}



	@Override
	public CartDto updateCartItem(String token, Long cartid, CartItemDto cartItem)
			throws Exception {
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		CartItem item = findCartItemById(cartid, userFromToken.getId());
		ProductDto product = productClientService.findById(cartItem.getProductId()).getBody();
		if(item==null || item.getId()==null) {
			throw new CartItemException("CartItem not found");
		}
		if(userFromToken.getId()!=item.getUserId()) {
			throw new CartItemException("Not an authenticated user");
		}
		item.setQuantity( cartItem.getQuantity());
		item.setPrice(product.getPrice()*item.getQuantity());
		item.setDiscountedPrice(product.getDiscountedPrice()*item.getQuantity());
        item.setSize(cartItem.getSize());
		
		cartItemRepository.save(item);

		return updateCartDetails(cartRepo.findByUserId(userFromToken.getId()));

	}

	@Override
	public CartItem findCartItemById(Long cartItemId, Long userId) throws CartItemException {
		Optional<CartItem> opt = cartItemRepository.findCartItemFromUserId(cartItemId, userId);

		
		if (opt.isPresent()) {
			return opt.get();
		}
		if(userId!=opt.get().getUserId()) {
			throw new CartItemException("Not an authenticated user");
		}
		throw new CartItemException("cartItem not found with id : " + cartItemId);
	}

	@Override /// During user registartion process only cart will be created
	public String createCart(Long user) throws Exception {
      // need not to check if cart is present in db with specific user
//		Cart cart = cartRepo.findByUserId(user);
//		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
//		if(cart!=null && user.equals(userFromToken.getId())) {
//			return CartMapper.cartMap(cart) ;
//		}else 	if(cart!=null && !user.equals(userFromToken.getId())) {
//			throw new Exception("This is conflict between user and auth token");
//		}
		Cart newCart = new Cart();
		newCart.setUser(user);
		newCart.setCartCode(UUID.randomUUID().toString());
	     cartRepo.save(newCart);
		return newCart.getCartCode();
	}

	@Override
	public CartDto findUserCart(String token, String cartCode) throws Exception {
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		Cart cart = cartRepo.findByCartCode(cartCode);
		if(cart==null || cart.getId()==null) {
			throw new Exception("Cart not found");
		}
		if(userFromToken.getId()!=cart.getUser()) {
			throw new Exception("Not an authenticated user");
		}

        return updateCartDetails(cart);
	}
	
	public CartDto updateCartDetails(Cart cart) {
		Double totalPrice = 0d;
		Double totalDiscountedPrice = 0d;
		Double totalItem = 0d;
		for (CartItem cartsItem : cart.getCartItems()) {
			totalPrice += cartsItem.getPrice();
			totalDiscountedPrice += cartsItem.getDiscountedPrice();
			totalItem += cartsItem.getQuantity();
		}

		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setDiscounte(totalPrice - totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		Cart savedCart = cartRepo.save(cart);
		
		List<CartItemDto> list = savedCart.getCartItems().stream().map((CartItem cartItem)->{
			
      
			  CartItemDto cartItemMap = CartMapper.cartItemMap(cartItem);
			  ProductDto productDto = productClientService.findById(cartItemMap.getProductId()).getBody();
			  cartItemMap.setProduct(productDto);
			  return cartItemMap;
			  
		}).distinct().toList();
		
		
		 CartDto cartDto = CartMapper.cartMap(savedCart);
		 cartDto.setCartItems(new HashSet<>(list));
		 return cartDto;
	}

	@Override
	public CartDto addCartItem(String token, CartItemDto req,String cartCode) throws Exception {
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		Cart cart = cartRepo.findByCartCode(cartCode);

		if(cart==null || cart.getId()==null) {
			throw new Exception("Cart not found");
		}
		if(userFromToken.getId()!=cart.getUser()) {
			throw new Exception("Not an authenticated user");
		}

		CartItem cartItem = cartItemRepository.isCartItemExist(cart, req.getProductId(), req.getSize(), userFromToken.getId());
		ProductDto productDto = productClientService.findById(req.getProductId()).getBody();

		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setProductId(req.getProductId());
			cartItem.setCart(cart);
			cartItem.setUserId(userFromToken.getId());

			cartItem.setQuantity(req.getQuantity());

			cartItem.setPrice(productDto.getPrice() * req.getQuantity());
			cartItem.setSize(req.getSize());
			cartItem.setDiscountedPrice(productDto.getDiscountedPrice() * req.getQuantity());

		} else {
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			cartItem.setPrice(productDto.getPrice() * cartItem.getQuantity());
			cartItem.setDiscountedPrice(productDto.getDiscountedPrice() * cartItem.getQuantity());
			
			
		}
		cartItemRepository.save(cartItem);

		
		Cart updatedCart  = cartRepo.findByCartCode(cartCode);
		 
        return 	updateCartDetails(updatedCart);
	}



	@Transactional
	@Override
	public void emptyCart(String token, String cartCode) throws Exception  {
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		Cart cart = cartRepo.findByCartCode(cartCode);
		if(cart==null || cart.getId()==null) {
			throw new Exception("Cart not found");
		}
		if(userFromToken.getId()!=cart.getUser()) {
			throw new Exception("Not an authenticated user");
		}  
		cartItemRepository.deleteByUserId(userFromToken.getId());
	
	}


@Transactional
	@Override
	public CartDto checkout(String token, AddressDto req, String cartCode) throws Exception {
		UserDto userFromToken = userClientService.getUserFromToken(token).getBody();
		Cart cart = cartRepo.findByCartCode(cartCode);
		
		if(cart==null || cart.getId()==null) {
			throw new Exception("Cart not found");
		}
		if(userFromToken.getId()!=cart.getUser()) {
			throw new Exception("Not an authenticated user");
		} 
		if(cart.getAddress()!=null) {
			req.setId(cart.getAddress().getId());
		}
		Address addressMap = CartMapper.addressMap(req);
		addressMap.setCart(cart);
		
		cart.setAddress(addressMap);
	
		
		if(req.getSaveAddress()) {
			 addressFeignClient.saveAddress(req, token);
		} 
		
		return updateCartDetails(cartRepo.save(cart));
	}


}
