package com.prashant.backendorderservice.order.service;

import com.prashant.backendorderservice.order.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.order.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.order.dto.response.OrderResponse;
import com.prashant.backendorderservice.order.dto.response.UpdateOrderStatusResponse;

public interface OrderServiceOperations {
    OrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderStatusResponse updateOrderStatusbyId(Long id, UpdateOrderStatusRequest request);
    OrderResponse getOrderById(Long id);
    void deleteOrderById(Long id);
}
