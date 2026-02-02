package com.prashant.backendorderservice.controller;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.model.OrderStatus;
import com.prashant.backendorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("orderplace")
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request){
        Long orderId = orderService.createOrder(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order created with id: " + orderId);
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        OrderResponse response =
                  orderService.updateOrderbyId(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("order")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam Long id){
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    @DeleteMapping("/order/remove/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

}
