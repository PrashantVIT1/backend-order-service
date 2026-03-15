package com.prashant.backendorderservice.orders.exception.custom;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException(Long orderId) {
        super("Order not found with id: " + orderId);
    }
}
