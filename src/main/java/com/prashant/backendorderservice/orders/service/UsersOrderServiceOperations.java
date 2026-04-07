package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;

import java.util.List;


public interface UsersOrderServiceOperations {
    public OrderResponse createOrder(CreateOrderRequest request);
    public UpdateOrderStatusResponse updateOrderStatusByUserId(Long id, UpdateOrderStatusRequest request);
    public List<OrderResponse> getOrdersByUserId();


}
