package com.ekart.productservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.entity.Product;

public interface ProductService {
    List<ProductDto> findAll();
    ProductDto findById(final Integer productId);
    ProductDto update(final ProductDto productDto);
    ProductDto update(final Integer productId, final ProductDto productDto);
    void deleteById(final Integer productId);
	Product save(Product product);
	Product findBySkuCode(String skuCode);
//	Page<Product> getAllProduct(String category, List<String> color, Integer minPrice, Integer maxPrice,
//			Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
	Page<ProductDto> getAllProduct(String category, List<String> color, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
	List<ProductDto> searchProduct(String query);
}
