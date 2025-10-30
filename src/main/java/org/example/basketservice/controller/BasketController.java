package org.example.basketservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.basketservice.entity.Basket;
import org.example.basketservice.repository.BasketRepository;
import org.example.basketservice.request.BasketRequest;
import org.example.basketservice.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    public ResponseEntity<Basket> save(@RequestBody BasketRequest request) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(basketService.CreateBasket(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable String id) {
        return ResponseEntity.ok(basketService.getBasketById(id));
    }
}
