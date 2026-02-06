package com.prashant.backendorderservice.exception;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException(Long orderId) {
        super("Order not found with id: " + orderId);
    }
}
