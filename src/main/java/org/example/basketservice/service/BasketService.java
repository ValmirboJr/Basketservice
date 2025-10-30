package org.example.basketservice.service;

import lombok.RequiredArgsConstructor;
import org.example.basketservice.client.response.PlatziProductResponse;
import org.example.basketservice.entity.Basket;
import org.example.basketservice.entity.Product;
import org.example.basketservice.enums.Status;
import org.example.basketservice.exceptions.BussinesException;
import org.example.basketservice.exceptions.DataNotFoundException;
import org.example.basketservice.repository.BasketRepository;
import org.example.basketservice.request.BasketRequest;
import org.example.basketservice.request.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket CreateBasket(BasketRequest basketRequest){
        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new BussinesException("Basket already exists");
                });

        List<Product> products = getProducts(basketRequest);

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
                .orElseThrow(() ->  new DataNotFoundException("Basket not found"));
    }

    public Basket update(String id,BasketRequest request) {
        Basket savedBasket = getBasketById(id);
        List<Product> products = getProducts(request);
        savedBasket.setProducts(products);
        savedBasket.calculateTotalPrice();
        return basketRepository.save(savedBasket);
    }

    private List<Product> getProducts(BasketRequest request) {
        List<Product> products = new ArrayList<>();
        request.products().forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductsById(productRequest.id());
            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(productRequest.quantity())
                            .build());
        });
        return products;
    }

    public Basket payBasket(String id, PaymentRequest request) {
        Basket savedBasket = getBasketById(id);
        savedBasket.setPaymentMethod(request.getPaymentMethod());
        savedBasket.setStatus(Status.SOLD);
        return basketRepository.save(savedBasket);
    }

    public void deleteBasket(String id) {
      basketRepository.delete(getBasketById(id));
    }
}
