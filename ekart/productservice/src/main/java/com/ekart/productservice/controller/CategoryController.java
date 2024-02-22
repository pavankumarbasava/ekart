package com.ekart.productservice.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.entity.Category;
import com.ekart.productservice.response.ResponseMessage;
import com.ekart.productservice.service.CategoryServiceImpl;

@RestController
@RequestMapping("/api/product/category")
public class CategoryController {

	private final static Logger log= LoggerFactory.getLogger(CategoryController.class);
	   
	    @Autowired
	    private  CategoryServiceImpl categoryService;

	    @GetMapping("/getCategory")
	    public ResponseEntity<List<CategoryDto>> findAll() {
	        log.info("CategoryDto List, controller; fetch all categories");
	     
	        return ResponseEntity.ok( categoryService.findAll());
	    }

	    @GetMapping("/{categoryId}")
	    public ResponseEntity<?> findById(@PathVariable("categoryId")
	                                                @NotBlank(message = "Input must not be blank")
	                                                @Valid final String categoryId) {
	        log.info("CategoryDto, resource; fetch category by id");
	        CategoryDto categoryDto = categoryService.findById(Integer.parseInt(categoryId));
	      
	   
	         
	        
	        if(categoryDto==null){
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("There is no category with that category id"));
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
	    }

	    @PostMapping("/save")
	    public ResponseEntity<Category> save(@RequestBody @NotNull(message = "Input must not be NULL")
	                                            @Valid  Category categoryDto) {
	        log.info("CategoryDto, resource; save category");
	        Category save=null;
	        try {
	        	  save = categoryService.save(categoryDto);
			} catch (Exception e) {
				log.error("error while saving category ",e);
			}
	       
	        return ResponseEntity.ok(save);
	    }

	    @PutMapping("/update")
	    public ResponseEntity<CategoryDto> update(@RequestBody
	                                              @NotNull(message = "Input must not be NULL")
	                                              @Valid final CategoryDto categoryDto) {
	        log.info("CategoryDto, resource; update category");
	        return ResponseEntity.ok(categoryService.update(categoryDto));
	    }

	    @PutMapping("/{categoryId}")
	    public ResponseEntity<CategoryDto> update(@PathVariable("categoryId")
	                                              @NotBlank(message = "Input must not be blank")
	                                              @Valid final String categoryId,
	                                              @RequestBody @NotNull(message = "Input must not be NULL")
	                                              @Valid final CategoryDto categoryDto) {
	        log.info("CategoryDto, resource; update category with categoryId");
	        return ResponseEntity.ok(categoryService.update(Integer.parseInt(categoryId), categoryDto));
	    }

	    @DeleteMapping("/{categoryId}")
	    public ResponseEntity<Boolean> deleteById(@PathVariable("categoryId") final String categoryId) {
	        log.info("Boolean, resource; delete category by id");
	        categoryService.deleteById(Integer.parseInt(categoryId));
	        return ResponseEntity.ok(true);
	    }
	
	
}
