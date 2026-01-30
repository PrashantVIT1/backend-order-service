package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.model.Order;

public interface OrderServiceOperations {
    String createOrder(Long CustomerId, String description);
    Order getOrderById(Long id);
}
