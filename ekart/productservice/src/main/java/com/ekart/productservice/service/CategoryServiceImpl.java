package com.ekart.productservice.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ekart.productservice.controller.CategoryController;
import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.entity.Category;
import com.ekart.productservice.entity.Size;
import com.ekart.productservice.mapper.CategoryMappingHelper;
import com.ekart.productservice.mapper.ProductMappingHelper;
import com.ekart.productservice.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final static Logger log= LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private  CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        log.info("Category List Service, fetch all category");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMappingHelper::map)
                .distinct()
                .toList();
    }

	@Override
	public CategoryDto findById(Integer categoryId) {
		Category category = null;
		try {
			category = categoryRepository.findById(categoryId).get();
		} catch (Exception e) {
			log.info("CategoryDto Service, fetch categories are null ", e);
			return null;
		}

		log.info("CategoryDto Service, fetch category by id ");

		CategoryDto categoryDto = null;
		if (category.getParentCategory() == null) {
			categoryDto = CategoryMappingHelper.simpleCategoryMapper(category);
			List<CategoryDto> list = category.getSubCategories().stream().map(

					(subCat) -> {

						CategoryDto simpleCategoryMapper = CategoryMappingHelper.simpleCategoryMapper(subCat);
						List<ProductDto> subCatProducts = subCat.getProducts().stream()
								.map(
										
										(product) -> {
											ProductDto simpleProductMap = ProductMappingHelper.simpleProductMap(product);
											List<Size> sizes = product.getSizes().stream().map((size)->CategoryMappingHelper.simplaSizeMapper(size)).distinct().toList();
											
											simpleProductMap.setSizes(new HashSet<>(sizes));
											return simpleProductMap;
										}
										
										
										
										
										).distinct().toList();
						simpleCategoryMapper.setProducts(subCatProducts);
						return simpleCategoryMapper;

					}).distinct().toList();
			categoryDto.setSubCategories(new HashSet<>(list));
//	        	  List<ProductDto> products = category.getProducts().stream().map(product->ProductMappingHelper.simpleProductMap(product)).distinct().toList();
//			        categoryDto.setProducts(products);
		} else {
			categoryDto = CategoryMappingHelper.map(category);

			List<ProductDto> products = category.getProducts().stream()
					.map(
							

							
							(product) -> {
								ProductDto simpleProductMap = ProductMappingHelper.simpleProductMap(product);
								List<Size> sizes = product.getSizes().stream().map(size->CategoryMappingHelper.simplaSizeMapper(size)).distinct().toList();
								
								simpleProductMap.setSizes(new HashSet<>(sizes));
								return simpleProductMap;
							}
							
							
							).distinct().toList();
			categoryDto.setProducts(products);
		}

		return categoryDto;
	}

    @Override
    public Category save(Category category ){
        log.info("CategoryDto, service; save category");
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("CategoryDto Service, update category");
        return CategoryMappingHelper
                .map(categoryRepository.save(CategoryMappingHelper.map(categoryDto)));
    }

    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryDto) {
        log.info("CategoryDto Service, update category with categoryId");
        return CategoryMappingHelper
                .map(categoryRepository.save(CategoryMappingHelper
                        .map(this.findById(categoryId))));
    }

    @Override
    public void deleteById(Integer categoryId) {
        log.info("Void Service, delete category by id");
        categoryRepository.deleteById(categoryId);
    }
}
