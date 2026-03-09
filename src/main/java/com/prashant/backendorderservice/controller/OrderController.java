package com.prashant.backendorderservice.controller;

import com.prashant.backendorderservice.config.swagger.OrderControllerDocs;
import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Orders", description = "Order management APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController implements OrderControllerDocs {

    private final OrderService orderService;

    // ================= CREATE ORDER =================

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    // ================= UPDATE ORDER STATUS =================

    @PatchMapping("/{id}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(
                orderService.updateOrderStatusbyId(id, request)
        );
    }

    // ================= GET ORDER =================

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ================= DELETE ORDER =================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
