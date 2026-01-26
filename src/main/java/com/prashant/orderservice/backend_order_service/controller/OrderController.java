package com.prashant.orderservice.backend_order_service.controller;

import com.prashant.orderservice.backend_order_service.model.Order;
import com.prashant.orderservice.backend_order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class OrderController {
    @Autowired
    private final OrderService  orderService;

    @GetMapping("order")
    public Order getOrderById(@RequestParam Long id){
        return orderService.getOrderById(id);
    }
    @PostMapping("orderplace")
    public String createOrder(){
        return orderService.createOrder("Initialized", LocalDateTime.now());
    }
}
