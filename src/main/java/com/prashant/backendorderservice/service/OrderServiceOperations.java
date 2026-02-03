package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.model.OrderStatus;

public interface OrderServiceOperations {
    Long createOrder(CreateOrderRequest request);
    UpdateOrderStatusResponse updateOrderStatusbyId(Long id, UpdateOrderStatusRequest request);
    OrderResponse getOrderById(Long id);
    void deleteOrderById(Long id);
}
