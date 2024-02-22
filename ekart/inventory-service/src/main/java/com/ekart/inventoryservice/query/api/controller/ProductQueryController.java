//package com.ekart.inventoryservice.query.api.controller;
//
//import java.util.List;
//
//import org.axonframework.messaging.responsetypes.ResponseTypes;
//import org.axonframework.queryhandling.QueryGateway;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ekart.commonservice.dto.ProductDto;
//import com.ekart.inventoryservice.query.api.queries.GetProductsQuery;
//
//@RestController
//@RequestMapping("/products")
//public class ProductQueryController {
//
//    private QueryGateway queryGateway;
//
//    public ProductQueryController(QueryGateway queryGateway) {
//        this.queryGateway = queryGateway;
//    }
//
//    @GetMapping
//    public List<ProductDto> getAllProducts() {
//        GetProductsQuery getProductsQuery =
//                new GetProductsQuery();
//
//        List<ProductDto> productRestModels =
//        queryGateway.query(getProductsQuery,
//                ResponseTypes.multipleInstancesOf(ProductDto.class))
//                .join();
//
//        return productRestModels;
//    }
//}
