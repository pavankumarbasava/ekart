package com.ekart.productservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.productservice.dtos.CategoryDto;
import com.ekart.productservice.dtos.ProductDto;
import com.ekart.productservice.entity.Product;
import com.ekart.productservice.response.ResponseMessage;
import com.ekart.productservice.service.CategoryServiceImpl;
import com.ekart.productservice.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	
	private final static Logger log= LoggerFactory.getLogger(CategoryController.class);
	
	
	
	    @Autowired
	    private  ProductService productService;
	    
	    @Autowired
	    private  CategoryServiceImpl categoryService;
	    

	    @GetMapping("/filter")
		public ResponseEntity<Page<ProductDto>> findProductByCategoryHandler(@Nullable @RequestParam String category,
				@Nullable @RequestParam List<String> color,@Nullable @RequestParam List<String> size,@Nullable @RequestParam Integer minPrice,
				@Nullable @RequestParam Integer maxPrice, @Nullable @RequestParam Integer minDiscount,@Nullable @RequestParam String sort, 
				@Nullable	@RequestParam String stock, @RequestParam Integer pageNumber,@RequestParam Integer pageSize){

					
					Page<ProductDto> res= productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort,stock,pageNumber,pageSize);
					
					System.out.println("complete products");
					return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
					
				}
		
		
		@GetMapping("/search")
		public ResponseEntity<List<ProductDto>> searchProductHandler(@RequestParam("q") String q){
			
			List<ProductDto> products=productService.searchProduct(q);
		
			return new ResponseEntity<List<ProductDto>>(products,HttpStatus.OK);
			
		}
		
	    @GetMapping("/getAllProducts")
	    public ResponseEntity<List<ProductDto>> findAll() {
	        log.info("ProductDto List, controller; fetch all categories");
	        return ResponseEntity.ok(productService.findAll());
	    }
	    
	    @PostMapping("/getProducts/{categoryId}")
	    public ResponseEntity<?> getProductsFromCategory(@PathVariable("categoryId")
                                                        @NotBlank(message = "Input must not be blank")
	    												@Valid final String categoryId) {
	    	  log.info("CategoryDto, resource; fetch category by id");
		        CategoryDto categoryDto = categoryService.findById(Integer.parseInt(categoryId));
		       
//		      .stream().map(ProductMappingHelper::map).distinct().toList();
//		        
//		        
//		        .stream()
//                .map(ProductMappingHelper::map)
//                .distinct()
////                .toSet();
//		        CategoryDto categoryDto=CategoryMappingHelper.map(category);
//		        List<ProductDto> list = category.getProducts().stream().map(product->ProductMappingHelper.simpleProductMap(product)).distinct().toList();
//		        categoryDto.setProducts(list);
		        if(categoryDto==null){
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("There is no category with that category id"));
		        }
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( categoryDto) ;
    }

	    @GetMapping("/getById")
	    public ResponseEntity<?> findById(@RequestParam("productId")
	                                               @NotBlank(message = "Input must not be blank!")
	                                               @Valid final Integer productId) {
	    	
	    	ProductDto product=null;
	    	try {
	    		log.info("Before find by id product");
	    		product = productService.findById(productId);
			} catch (Exception e) {
				log.error("There is an issue while fetching product using id : "+productId+" ",e);
				return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Product all ready exists"));
				
			}
	        log.info("ProductDto, resource; fetch product by id : "+productId);
	        return ResponseEntity.ok(product);
	    }

	    @PostMapping("/save")
	    public ResponseEntity<?> save(@RequestBody
	                                           @NotNull(message = "Input must not be NULL!")
	                                           @Valid final Product product) {
	        log.info("ProductDto, resource; save product");
	        product.setCreateAt(LocalDateTime.now());
	        if(ObjectUtils.isEmpty(productService.findBySkuCode(product.getSku()))){
	            return ResponseEntity.ok(productService.save(product));
	        }
	        return  ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Product all ready exists"));
	    }

	    @PutMapping("/update")
	    public ResponseEntity<ProductDto> update(@RequestBody
	                                             @NotNull(message = "Input must not be NULL!")
	                                             @Valid final ProductDto productDto) {
	        log.info("ProductDto, resource; update product");
	        return ResponseEntity.ok(productService.update(productDto));
	    }

	    @PutMapping("/update/{productId}")
	    public ResponseEntity<ProductDto> update(@PathVariable("productId")
	                                             @NotBlank(message = "Input must not be blank!")
	                                             @Valid final String productId,
	                                             @RequestBody
	                                             @NotNull(message = "Input must not be NULL!")
	                                             @Valid final ProductDto productDto) {
	        log.info("ProductDto, resource; update product with productId");
	        return ResponseEntity.ok(productService.update(Integer.parseInt(productId), productDto));
	    }

	    @DeleteMapping("/{productId}")
	    public ResponseEntity<Boolean> deleteById(@PathVariable("productId") final String productId) {
	        log.info("Boolean, resource; delete product by id");
	        productService.deleteById(Integer.parseInt(productId));
	        return ResponseEntity.ok(true);
	    }
	
	
}
