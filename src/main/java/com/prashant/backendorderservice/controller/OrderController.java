package com.prashant.backendorderservice.controller;

import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class OrderController {

    private final OrderService  orderService;

    @GetMapping("order")
    public ResponseEntity<Order> getOrderById(@RequestParam Long id){
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    @PostMapping("orderplace")
    public ResponseEntity<String> createOrder(@RequestParam Long customerId, @RequestParam String description){
        return new ResponseEntity<>(orderService.createOrder(customerId, description), HttpStatus.CREATED);
    }
}
