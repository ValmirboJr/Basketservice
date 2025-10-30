package org.example.basketservice.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class customerErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new DataNotFoundException("Product not found");
                default:
                    return new Exception("Exception while decoding getting product");
        }
    }
}
