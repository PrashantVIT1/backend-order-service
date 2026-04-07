package com.prashant.backendorderservice.orders.controller;


import com.prashant.backendorderservice.orders.config.swagger.UsersOrderControllerDocs;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;

import com.prashant.backendorderservice.orders.service.UsersOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Order management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/orders")
public class UsersOrderController implements UsersOrderControllerDocs {

    private final UsersOrderService usersOrderService;

    // ================= CREATE ORDER =================

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersOrderService.createOrder(request));
    }

    // ================= UPDATE ORDER STATUS BY USER ID =================

    @PatchMapping("/{id}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(
                usersOrderService.updateOrderStatusByUserId(id, request)
        );
    }

    // ================= GET ORDER=================

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(usersOrderService.getOrdersByUserId());
    }

    // ================= GET ORDER BY ID=================

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(usersOrderService.getOrderByUserId(id));
    }

    // ================= DELETE ORDER =================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        usersOrderService.deleteOrderByUserId(id);
        return ResponseEntity.noContent().build();
    }


}
