package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;

public interface OrderServiceOperations {
    OrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderStatusResponse updateOrderStatusbyId(Long id, UpdateOrderStatusRequest request);
    OrderResponse getOrderById(Long id);
    void deleteOrderById(Long id);
}
