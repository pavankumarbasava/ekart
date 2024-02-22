package com.ekart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	@Query("select c from Category c  , Product p  where p.category=c.id and c.id=:categoryId")
     Category findByCategoryId(Integer categoryId);

	

}
