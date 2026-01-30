package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.model.Order;

public interface OrderServiceOperations {
    Long createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);
}
