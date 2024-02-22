//package com.ekart.inventoryservice.query.api.projection;
//
//import java.util.List;
//
//import org.axonframework.queryhandling.QueryHandler;
//import org.springframework.stereotype.Component;
//
//import com.ekart.commonservice.dto.ProductDto;
//import com.ekart.inventoryservice.entity.Product;
//import com.ekart.inventoryservice.mapper.ProductMappingHelper;
//import com.ekart.inventoryservice.query.api.queries.GetProductsQuery;
//import com.ekart.inventoryservice.repository.InventoryRepository;
//
//import lombok.AllArgsConstructor;
//
//@Component
//@AllArgsConstructor
//public class InventoryProjection {
//
//    private InventoryRepository productRepository;
//
//  
//
//    @QueryHandler
//    public List<ProductDto> handle(GetProductsQuery getProductsQuery) {
//        List<Product> products =
//                productRepository.findAll();
//
//        List<ProductDto> productRestModels =products.stream().map(product->
//        	  ProductMappingHelper.map(product)
//        ).distinct().toList();
//             
//
//        return productRestModels;
//    }
//}
