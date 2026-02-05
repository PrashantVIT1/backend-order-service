package com.prashant.backendorderservice.controller;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Tag(name = "Orders", description = "Order management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order")
    @PostMapping("orderplace")
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request){
        Long orderId = orderService.createOrder(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Order created with id: " + orderId);
    }

    @Operation(summary = "Update order status")
    @PatchMapping("orders/{id}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return new ResponseEntity<>(orderService.updateOrderStatusbyId(id,request),HttpStatus.OK);
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("order")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam Long id){
        return new ResponseEntity<>(orderService.getOrderById(id),HttpStatus.OK);
    }

    @Operation(summary = "Delete order by ID")
    @DeleteMapping("order/remove/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

}
