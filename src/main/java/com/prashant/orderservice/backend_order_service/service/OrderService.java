package com.prashant.orderservice.backend_order_service.service;

import com.prashant.orderservice.backend_order_service.model.Order;
import com.prashant.orderservice.backend_order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public ArrayList<Order> arr= new ArrayList<>();
    public String createOrder(String status, LocalDateTime createdAt){

        Order order = new Order();
        order.setStatus(status);
        order.setCreatedAt(createdAt);
        arr.add(order);
        Order obj = arr.get(arr.size()-1);
        System.out.println(obj.getStatus());
        return "Success";
    }

    public Order getOrderById(Long id){
        Order obj = arr.get(arr.size()-1);
        System.out.println(obj.getId()+" "+obj.getStatus()+" "+obj.getCreatedAt());
        return arr.get(arr.size()-1); //temporary
    }
}
