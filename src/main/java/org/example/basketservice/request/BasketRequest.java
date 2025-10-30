package org.example.basketservice.request;

import java.util.List;

public record BasketRequest(Long clientId, List<ProductRequest> products) {
}
