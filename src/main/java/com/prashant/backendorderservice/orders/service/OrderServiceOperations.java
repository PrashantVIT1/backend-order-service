package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;

import java.util.List;

public interface OrderServiceOperations {
    OrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderStatusResponse updateOrderStatusById(Long id, UpdateOrderStatusRequest request);
    List<OrderResponse> getOrders();
    OrderResponse getOrderById(Long id);
    void deleteOrderById(Long id);
}
