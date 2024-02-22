package com.ekart.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.productservice.entity.WishList;

public interface WishListRepo extends JpaRepository<WishList, Long> {

	
	@Query("select w.products from WishList w where w.userId=:id")
	List<Long> findWishListByUserId(@Param("id") Long id);

	@Modifying
	@Query("delete from  WishList w where w.userId=:id AND w.products=:productId")
	void deleteWishList(@Param("productId")  Long productId, @Param("id") Long id);
	
	@Query("select w from WishList w where w.userId=:id AND w.products=:productId")
	WishList findByuserNameProductId(@Param("productId")  Long productId, @Param("id") Long id);

}
