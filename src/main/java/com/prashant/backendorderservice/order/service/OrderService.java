package com.prashant.backendorderservice.order.service;

import com.prashant.backendorderservice.order.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.order.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.order.dto.response.OrderResponse;
import com.prashant.backendorderservice.order.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.order.exception.OrderNotFoundException;
import com.prashant.backendorderservice.order.entity.Order;
import com.prashant.backendorderservice.order.entity.OrderStatus;
import com.prashant.backendorderservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceOperations{

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setDescription(request.getDescription());

        OrderStatus status = OrderStatus.CREATED;
        if (request.getStatus() != null) {
            status = OrderStatus.valueOf(request.getStatus().toUpperCase());
        }
        order.setStatus(status);

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .customerId(savedOrder.getCustomerId())
                .description(savedOrder.getDescription())
                .status(savedOrder.getStatus().name())
                .build();
    }

    public UpdateOrderStatusResponse updateOrderStatusbyId(Long id, UpdateOrderStatusRequest request){
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));


        response.setStatus(request.getStatus());

        orderRepository.save(response);

        return UpdateOrderStatusResponse.builder()
                .id(response.getId())
                .status(response.getStatus().name())
                .updatedAt(response.getUpdatedAt())
                .build();
    }

    public OrderResponse getOrderById(Long id) {
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));
        return OrderResponse.builder()
                .id(response.getId())
                .customerId(response.getCustomerId())
                .description(response.getDescription())
                .status(response.getStatus().name())
                .build();
    }


    public void deleteOrderById(Long id) {
        Order response = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));
        orderRepository.delete(response);
    }


}
