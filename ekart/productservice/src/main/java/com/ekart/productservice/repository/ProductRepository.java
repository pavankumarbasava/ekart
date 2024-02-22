package com.ekart.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ekart.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("select p from  Product p where p.sku=:skuCode")
	Product findBySkuCode(String skuCode);

	
//	
//	@Query("SELECT p FROM Product p " +
//	        "WHERE (p.category.name = :category OR :category = '') " +
//	        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
//		    "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " +
//		    "ORDER BY " +
//		    "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
//		    "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
	
	@Query("select p from Product p "
	        + "where (p.category.categoryTitle=:categoryName OR :categoryName = ''  ) "
			+ "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "
			+ "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " + "ORDER BY "
			+ "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, "
			+ "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC"

	)
	List<Product> filterProducts(
	        @Param("categoryName") String categoryName,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			@Param("minDiscount") Integer minDiscount,
			@Param("sort") String sort
			);
////
	
	@Query("SELECT p From Product p where LOWER(p.productTitle) Like %:query% OR LOWER(p.description) Like %:query% OR LOWER(p.brand) LIKE %:query% OR LOWER(p.category.categoryTitle) LIKE %:query%")
	public List<Product> searchProduct(@Param("query")String query);
	
	
	

}
