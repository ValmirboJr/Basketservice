package org.example.basketservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basketservice.client.PlatziStoreClient;
import org.example.basketservice.client.response.PlatziProductResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts(){
        log.info("Getting All Products");
        return  platziStoreClient.getAllProducts();
    }

    @Cacheable(value = "products", key = "productId")
    public PlatziProductResponse getProductsById(Long productId){
        log.info("Getting Products by ID: {}", productId);
        return platziStoreClient.getProductsById(productId);
    }
}
