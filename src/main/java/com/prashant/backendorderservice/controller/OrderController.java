package com.prashant.backendorderservice.controller;

import com.prashant.backendorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class OrderController {
    @Autowired
    private final OrderService  orderService;

    @GetMapping("order")
    public Object getOrderById(@RequestParam Long id){

        return orderService.getOrderById(id);
    }
    @PostMapping("orderplace")
    public String createOrder(@RequestParam Long customerId,@RequestParam String description){
        return orderService.createOrder(customerId, description);
    }
}
