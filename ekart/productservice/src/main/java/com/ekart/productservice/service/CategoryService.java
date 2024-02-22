package com.ekart.productservice.service;

import java.util.List;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.entity.Category;

public interface CategoryService {

	CategoryDto findById(Integer categoryId);

	List<CategoryDto> findAll();



	CategoryDto update(Integer categoryId, CategoryDto categoryDto);

	void deleteById(Integer categoryId);

	CategoryDto update(CategoryDto categoryDto);

	Category save(Category category);

}
