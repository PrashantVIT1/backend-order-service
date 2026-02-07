package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.exception.OrderNotFoundException;
import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceOperations{

    private final OrderRepository orderRepository;

    public Long createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setDescription(request.getDescription());

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
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
