package org.example.basketservice.client;

import org.example.basketservice.client.response.PlatziProductResponse;
import org.example.basketservice.exceptions.customerErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "PlatziStoreClient", url ="${basket.client.platzi}", configuration = {customerErrorDecoder.class})
public interface PlatziStoreClient {

    @GetMapping("/products/{id}")
    PlatziProductResponse getProductsById(Long id);

    @GetMapping("/products")
    List<PlatziProductResponse> getAllProducts();
}
