package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;

public interface OrderServiceOperations {
    OrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderStatusResponse updateOrderStatusbyId(Long id, UpdateOrderStatusRequest request);
    OrderResponse getOrderById(Long id);
    void deleteOrderById(Long id);
}
