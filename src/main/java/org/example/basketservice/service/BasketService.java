package org.example.basketservice.service;

import lombok.RequiredArgsConstructor;
import org.example.basketservice.client.response.PlatziProductResponse;
import org.example.basketservice.entity.Basket;
import org.example.basketservice.entity.Product;
import org.example.basketservice.enums.Status;
import org.example.basketservice.repository.BasketRepository;
import org.example.basketservice.request.BasketRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket CreateBasket(BasketRequest basketRequest){
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new IllegalArgumentException("Basket already exists");
                });

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductsById(productRequest.id());

            products.add(Product.builder()
                            .id(platziProductResponse.id())
                            .title(platziProductResponse.title())
                            .price(platziProductResponse.price())
                            .quantity(productRequest.quantity())
                            .build());
        });

        Basket basket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();
        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException("Basket not found"));
    }
}
