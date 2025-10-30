package org.example.basketservice.entity;

import lombok.*;
import org.example.basketservice.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "basket")
public class Basket {

    @Id
    private String id;

    private Long client;

    private BigDecimal totalprice;
    
    private List<Product> products;

    private Status status;

    public void calculateTotalPrice(){
        this.totalprice = products.stream()
                .map(product-> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
