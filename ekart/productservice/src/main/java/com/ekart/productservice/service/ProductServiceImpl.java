package com.ekart.productservice.service;

import java.util.HashSet;
//import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.entity.Product;
import com.ekart.productservice.entity.Size;
import com.ekart.productservice.exception.ProductNotFoundException;
import com.ekart.productservice.mapper.CategoryMappingHelper;
import com.ekart.productservice.mapper.ProductMappingHelper;
import com.ekart.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private  ProductRepository productRepository;

	@Override
	public List<ProductDto> findAll() {
		log.info("ProductDto List, service, fetch all products");
		return productRepository.findAll().stream().map(ProductMappingHelper::map).distinct().toList();
	}

	@Override
	public ProductDto findById(Integer productId) {
		log.info("ProductDto, service; fetch product by id");
		return productRepository.findById(productId).map(ProductMappingHelper::map).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product with id[%d] not found", productId)));
	}

	@Override
	public Product save(Product product) {
		log.info("ProductDto, service; save product");
		return productRepository.save(product);
	}

	@Override
	public ProductDto update(ProductDto productDto) {
		log.info("ProductDto, service; update product");
		return ProductMappingHelper.map(productRepository.save(ProductMappingHelper.map(productDto)));
	}

	@Override
	public ProductDto update(Integer productId, ProductDto productDto) {
		log.info("ProductDto, service; update product with productId");
		return ProductMappingHelper.map(productRepository.save(ProductMappingHelper.map(this.findById(productId))));
	}

	@Override
	public void deleteById(Integer productId) {
		log.info("Void, service; delete product by id");
		this.productRepository.delete(ProductMappingHelper.map(this.findById(productId)));
	}
	
	@Override
	public Product findBySkuCode(String skuCode) {
		log.info("ProductDto, service; save product");
		return productRepository.findBySkuCode(skuCode);
	}

	@Override
	public Page<ProductDto> getAllProduct(String categoryName, List<String> colors, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> productList = productRepository.filterProducts(categoryName, minPrice, maxPrice, minDiscount, sort);
		
		
		List<ProductDto> products = productList.stream()
				.map(
						

						
						(product) -> {
							ProductDto simpleProductMap = ProductMappingHelper.map(product);
							List<Size> sizes = product.getSizes().stream().map(sizeObj->CategoryMappingHelper.simplaSizeMapper(sizeObj)).distinct().toList();
							
							simpleProductMap.setSizes(new HashSet<>(sizes));
							return simpleProductMap;
						}
						
						
						).distinct().toList();
		
		if (!colors.isEmpty()) {
			products = products.stream()
			        .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
			        .collect(Collectors.toList());
		
		
		} 

		if(stock!=null) {

			if(stock.equalsIgnoreCase("true")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if (stock.equalsIgnoreCase("false")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());				
			}
				
					
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<ProductDto> pageContent = products.subList(startIndex, endIndex);
		Page<ProductDto> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
	    return filteredProducts; // If color list is empty, do nothing and return all products
		
	}
	@Override
	public List<ProductDto> searchProduct(String query) {
		List<ProductDto> products=productRepository.searchProduct(query)
				.stream().map(ProductMappingHelper::map).distinct().toList();
		
		return products;
	}


}
