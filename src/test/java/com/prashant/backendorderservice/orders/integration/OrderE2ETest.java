package com.prashant.backendorderservice.orders.integration;

import com.prashant.backendorderservice.auth.support.AuthenticatedE2ETest;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.entity.Order;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderE2ETest extends AuthenticatedE2ETest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    // ================= CREATE =================

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(1L);
        request.setDescription("MacBook");

        mockMvc.perform(post("/user/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.description").value("MacBook"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void shouldReturn401WhenNoToken() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(1L);
        request.setDescription("MacBook");

        mockMvc.perform(post("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    // ================= GET =================

    @Test
    void shouldGetOrderById() throws Exception {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setUserId(1L);
        order.setDescription("MacBook");
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        mockMvc.perform(get("/admin/orders/" + order.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.description").value("MacBook"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void shouldReturn404WhenOrderNotFound() throws Exception {
        mockMvc.perform(get("/admin/orders/999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    // ================= UPDATE STATUS =================

    @Test
    void shouldUpdateOrderStatus() throws Exception {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setUserId(1L);
        order.setDescription("MacBook");
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.SHIPPED);

        mockMvc.perform(patch("/admin/orders/" + order.getId() + "/status")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPED"));
    }

    // ================= DELETE =================

    @Test
    void shouldDeleteOrderSuccessfully() throws Exception {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setUserId(1L);
        order.setDescription("MacBook");
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        mockMvc.perform(delete("/admin/orders/" + order.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentOrder() throws Exception {
        mockMvc.perform(delete("/admin/orders/999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }
}