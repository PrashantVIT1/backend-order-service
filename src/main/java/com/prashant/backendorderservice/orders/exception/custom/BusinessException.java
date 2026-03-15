package com.prashant.backendorderservice.orders.exception.custom;

public abstract class BusinessException extends RuntimeException {
    protected BusinessException(String message) {
        super(message);
    }
}
