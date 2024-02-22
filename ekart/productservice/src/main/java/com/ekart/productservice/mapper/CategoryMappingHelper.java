package com.ekart.productservice.mapper;

import java.util.Optional;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.entity.Category;
import com.ekart.productservice.entity.Size;

public interface CategoryMappingHelper {
    // map Category -> CategoryDto
    public static CategoryDto map(final Category category) {
        final var parentCategory = Optional.ofNullable(category.getParentCategory())
                .orElseGet(Category::new);
        return CategoryDto.builder()
                .categoryId(category.getId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                .parentCategoryDto(
                        CategoryDto.builder()
                                .categoryId(parentCategory.getId())
                                .categoryTitle(parentCategory.getCategoryTitle())
                                .imageUrl(parentCategory.getImageUrl())
                                .build()
                )
                .build();
    }
    
    public static CategoryDto simpleCategoryMapper(final Category category) {
        final var parentCategory = Optional.ofNullable(category.getParentCategory())
                .orElseGet(Category::new);
        return CategoryDto.builder()
                .categoryId(category.getId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                
                .build();
    }
    
    public static Size simplaSizeMapper(final Size size) {
       
        return Size.builder()
                .name(size.getName())
                .quantity(size.getQuantity())
                .build();
    }



    // map CategoryDto -> Category
    public static Category map(CategoryDto categoryDto) {
        final var parentCategoryDto = Optional.ofNullable(categoryDto.getParentCategoryDto())
                .orElseGet(CategoryDto::new);
        return Category.builder()
                .id(categoryDto.getCategoryId())
                .categoryTitle(categoryDto.getCategoryTitle())
                .imageUrl(categoryDto.getImageUrl())
                .parentCategory(Category.builder()
                        .id(parentCategoryDto.getCategoryId())
                        .categoryTitle(parentCategoryDto.getCategoryTitle())
                        .imageUrl(parentCategoryDto.getImageUrl())
                        .build())
                .build();
    }




}
