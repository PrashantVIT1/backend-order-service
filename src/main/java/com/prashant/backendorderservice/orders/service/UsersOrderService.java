package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.orders.entity.Order;
import com.prashant.backendorderservice.orders.entity.OrderStatus;

import com.prashant.backendorderservice.orders.exception.custom.OrderStatusInvalidException;
import com.prashant.backendorderservice.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersOrderService implements UsersOrderServiceOperations {
    private final OrderRepository orderRepository;
    private final AuthUtil authUtil;

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setDescription(request.getDescription());

        User user = authUtil.getAuthenticatedUser();
        order.setUserId(user.getId());

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

    public UpdateOrderStatusResponse updateOrderStatusByUserId(Long id, UpdateOrderStatusRequest request){

        User user = authUtil.getAuthenticatedUser();
        Order response = orderRepository.findByUserIdAndId(user.getId(),id);

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


    @Transactional(readOnly=true)
    public List<OrderResponse> getOrdersByUserId() {

        return orderRepository.findAllByUserId(authUtil.getAuthenticatedUser().getId()).stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderByUserId(Long id) {

        User user = authUtil.getAuthenticatedUser();
        Order response = orderRepository.findByUserIdAndId(user.getId(),id);

        return OrderResponse.builder()
                .id(response.getId())
                .customerId(response.getCustomerId())
                .description(response.getDescription())
                .status(response.getStatus())
                .build();
    }

    public void deleteOrderByUserId(Long id) {
        User user = authUtil.getAuthenticatedUser();
        Order response = orderRepository.findByUserIdAndId(user.getId(), id);
        orderRepository.delete(response);
    }

}
