package com.ekart.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.cart.dtos.ProductDto;
import com.ekart.cart.entity.Cart;
import com.ekart.cart.entity.CartItem;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("Select c from Cart c where c.user=:id")
	Cart findByUserId(@Param("id")  Long id);

	
	Cart findByCartCode(String cartCode);
	

}
