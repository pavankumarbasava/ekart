package com.ekart.productservice.mapper;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.entity.Category;
import com.ekart.productservice.entity.Product;

public interface ProductMappingHelper {
    public static ProductDto map(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .productTitle(product.getProductTitle())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .color(product.getColor())
                .brand(product.getBrand())
                .discountedPrice(product.getDiscountedPrice())
                .discountPersent(product.getDiscountPersent())
                .description(product.getDescription())
                .stock(product.getStock())
                .sizes(product.getSizes())
                
                .categoryDto(
                        CategoryDto.builder()
                                .categoryId(product.getCategory().getId())
                                .categoryTitle(product.getCategory().getCategoryTitle())
                                .imageUrl(product.getCategory().getImageUrl())
                                .build())
                .build();
    }
    
    public static ProductDto simpleProductMap(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .productTitle(product.getProductTitle())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .color(product.getColor())
                .brand(product.getBrand())
                .discountedPrice(product.getDiscountedPrice())
                .discountPersent(product.getDiscountPersent())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .stock(product.getStock())
                .sizes(product.getSizes())
                
                
//                .categoryDto(
//                        CategoryDto.builder()
//                                .categoryId(product.getCategory().getId())
//                                .categoryTitle(product.getCategory().getCategoryTitle())
//                                .imageUrl(product.getCategory().getImageUrl())
//                                .build())
                .build();
    }


    public static Product map(final ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .productTitle(productDto.getProductTitle())
                .imageUrl(productDto.getImageUrl())
                .sku(productDto.getSku())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .color(productDto.getColor())
                .brand(productDto.getBrand())
                .discountedPrice(productDto.getDiscountedPrice())
                .discountPersent(productDto.getDiscountPersent())
                .description(productDto.getDescription())
                .stock(productDto.getStock())
                .category(
                        Category.builder()
                                .id(productDto.getCategoryDto().getCategoryId())
                                .categoryTitle(productDto.getCategoryDto().getCategoryTitle())
                                .imageUrl(productDto.getCategoryDto().getImageUrl())
                                .build())
                .build();
    }

}
