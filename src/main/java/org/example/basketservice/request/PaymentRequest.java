package org.example.basketservice.request;

import lombok.Getter;
import lombok.Setter;
import org.example.basketservice.enums.PaymentMethod;

@Getter
@Setter
public class PaymentRequest {
    private PaymentMethod paymentMethod;
}
