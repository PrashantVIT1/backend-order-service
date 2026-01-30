package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceOperations{

    private final OrderRepository orderRepository;

    public String createOrder(Long customerId, String description){

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setDescription(description);
        orderRepository.save(order);
        return "Success";
    }

    public Order getOrderById(Long id) {


        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
}
