package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.orders.exception.custom.OrderNotFoundException;
import com.prashant.backendorderservice.orders.entity.Order;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.exception.custom.OrderStatusInvalidException;
import com.prashant.backendorderservice.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceOperations{

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setDescription(request.getDescription());

        // if null default to CREATED, otherwise use what Jackson already validated
        OrderStatus status = request.getStatus() != null
                ? request.getStatus()
                : OrderStatus.CREATED;
        order.setStatus(status);

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .customerId(savedOrder.getCustomerId())
                .description(savedOrder.getDescription())
                .status(savedOrder.getStatus())
                .build();
    }

    public UpdateOrderStatusResponse updateOrderStatusById(Long id, UpdateOrderStatusRequest request){
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));

        try {
            response.setStatus(request.getStatus());
            orderRepository.save(response);
        } catch (IllegalArgumentException ex) {
            throw new OrderStatusInvalidException(
                    "Invalid status value: " + request.getStatus()
            );
        }

        return UpdateOrderStatusResponse.builder()
                .id(response.getId())
                .status(response.getStatus())
                .updatedAt(response.getUpdatedAt())
                .build();
    }


    private OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .description(order.getDescription())
                .status(order.getStatus())
                .build();
    }
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::toOrderResponse)
                .toList();
    }


    public OrderResponse getOrderById(Long id) {
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));
        return OrderResponse.builder()
                .id(response.getId())
                .customerId(response.getCustomerId())
                .description(response.getDescription())
                .status(response.getStatus())
                .build();
    }


    public void deleteOrderById(Long id) {
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));
        orderRepository.delete(response);
    }


}
