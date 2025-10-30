package org.example.basketservice.repository;

import org.example.basketservice.entity.Basket;
import org.example.basketservice.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BasketRepository extends MongoRepository<Basket,String> {
    Optional<Basket> findByClientAndStatus(Long client, Status status);
}
