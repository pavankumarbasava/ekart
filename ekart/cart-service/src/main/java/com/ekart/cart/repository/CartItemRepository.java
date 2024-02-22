package com.ekart.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.cart.entity.Cart;
import com.ekart.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
//	public CartItem isCartItemExist(@Param("cart")Cart cart,@Param("product") String productId,@Param("size")String size, @Param("userId")Long userId);
//	
	
	@Query("Select c from CartItem c where c.id=:cartItemId  and c.userId=:userId")
	Optional<CartItem> findCartItemFromUserId(@Param("cartItemId")  Long cartItemId,
			@Param("userId")  Long userId);
	
	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.productId=:productId And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart")Cart cart,@Param("productId") Integer productId,@Param("size")String size, @Param("userId")Long userId);

	@Modifying
	@Query("Delete from CartItem ci where ci.userId=:userId")
	void deleteByUserId(@Param("userId") Long userId);
	

}
