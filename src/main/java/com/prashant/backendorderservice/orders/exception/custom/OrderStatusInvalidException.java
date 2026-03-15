package com.prashant.backendorderservice.orders.exception.custom;

public class OrderStatusInvalidException extends BusinessException {
    public OrderStatusInvalidException(String message) {
        super(message);
    }
}
