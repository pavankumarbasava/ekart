package com.ekart.order.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.commonservice.dto.CartDto;
import com.ekart.commonservice.dto.CartItemDto;
import com.ekart.commonservice.dto.UserDto;
import com.ekart.order.client.CartFeignClient;
import com.ekart.order.client.UserFeignClient;
import com.ekart.order.entity.Address;
import com.ekart.order.entity.Order;
import com.ekart.order.entity.OrderItem;
import com.ekart.order.entity.OrderStatus;
import com.ekart.order.repository.OrderRepository;
import com.ekart.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
    private CartFeignClient cartFeignClient;
	
	@Autowired
	 private UserFeignClient userFeignClient;
	
//	@Autowired
//	  private  StreamBridge streamBridge;
	  
	@Autowired
	private OrderRepository orderRepo;
	
	@Transactional(rollbackOn = Exception.class)
	public Order buildOrder(String cartCode,String token,String orderCode) throws Exception{
		
	       // BeanUtils.copyProperties(event,order);
	        CartDto cart = cartFeignClient.findUserCartHandler(cartCode,token).getBody();
	        UserDto user = userFeignClient.getUserFromToken(token).getBody();
	        Order order = new Order();
	      
	        if(cart.getUser()!=user.getId()) {
	        	throw new Exception("User not match exception");
	        }
	       
	        
	      //	BeanUtils.copyProperties(cart.getAddress(), addressDto);
	        
	        order.setDeliveryDate(LocalDateTime.now());
	        order.setOrderDate(LocalDateTime.now());
	        order.setTotalPrice(cart.getTotalPrice());
	        order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
	        order.setDiscounte(cart.getDiscounte());
	        order.setOrderStatus(OrderStatus.valueOf("CREATED"));
	        order.setTotalItem(cart.getTotalItem());
	        order.setUser(user.getId());
	        order.setOrderCode(orderCode);
	        
	        
	//        orderRepo.save(order);
	        
	        Address address= new Address();

	        BeanUtils.copyProperties(cart.getAddress(), address);
	        address.setId(null);
	        order.setShippingAddress(address);
	        address.setOrder(order);
	        
	        List<OrderItem> list = cart.getCartItems().stream().map((CartItemDto cartItemDto)->{
				OrderItem orderItem= new OrderItem();
	        	BeanUtils.copyProperties(cartItemDto, orderItem);
	        	orderItem.setId(null);
	        	orderItem.setOrder(order);
			//	  CartItemDto cartItemMap = CartMapper.cartItemMap(cartItemDto);
				 // ProductDto productDto = productClientService.findById(cartItemMap.getProductId()).getBody();
				 // cartItemMap.setProduct(productDto);
				  return orderItem;
				  
			}).distinct().toList();
	        
	        order.setOrderItems(list);
	     
	     return  order;
	      //   updateInventoryEvent(order.getOrderCode(),token);
	   	 
	
	}



	

//	private void updateInventoryEvent(String orderCode, String token)  throws Exception{
//		
//		try {
//			var result = streamBridge.send("updateInventoryEvent-out-0", new CommonEvent(orderCode,token));
//			System.out.println("Result "+result);
//		} catch (Exception e) {
//			throw new Exception(e);// TODO: handle exception
//		}
//		 
//		 
//		 
//	}
}