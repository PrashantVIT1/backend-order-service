package com.prashant.backendorderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.exception.OrderNotFoundException;
import com.prashant.backendorderservice.model.OrderStatus;
import com.prashant.backendorderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= CREATE ORDER =================

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setDescription("Laptop");
        request.setCustomerId(1L);

        when(orderService.createOrder(any()))
                .thenReturn(101L);

        mockMvc.perform(post("/orderplace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Order created with id: 101"));

        verify(orderService).createOrder(any(CreateOrderRequest.class));
        verifyNoMoreInteractions(orderService);
    }

//     ================= UPDATE STATUS =================

    @Test
    void shouldUpdateOrderStatusSuccessfully() throws Exception {

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.SHIPPED);

        UpdateOrderStatusResponse response = UpdateOrderStatusResponse.builder()
                .id(1L)
                .status("SHIPPED")
                .updatedAt(LocalDateTime.now())
                .build();

        when(orderService.updateOrderStatusbyId(
                eq(1L),
                any(UpdateOrderStatusRequest.class)
        )).thenReturn(response);

        mockMvc.perform(patch("/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("SHIPPED"));

        verify(orderService).updateOrderStatusbyId(eq(1L), any(UpdateOrderStatusRequest.class));
        verifyNoMoreInteractions(orderService);
    }


    // ================= GET ORDER =================

@Test
void shouldReturnOrderWhenExists() throws Exception {

    OrderResponse response = OrderResponse.builder()
            .id(1L)
            .customerId(1L)
            .description("Laptop")
            .status("SHIPPED").build();


    when(orderService.getOrderById(1L))
            .thenReturn(response);

    mockMvc.perform(get("/order")
                    .param("id", "1"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.description").value("Laptop"));

    verify(orderService).getOrderById(1L);
    verifyNoMoreInteractions(orderService);
}


    @Test
    void shouldReturn404WhenOrderNotFound() throws Exception {

        when(orderService.getOrderById(1L))
                .thenThrow(new OrderNotFoundException(1L));

        mockMvc.perform(get("/order")
                        .param("id", "1"))
                .andExpect(status().isNotFound());

        verify(orderService).getOrderById(1L);
        verifyNoMoreInteractions(orderService);
    }

//    // ================= DELETE ORDER =================

    @Test
    void shouldDeleteOrderSuccessfully() throws Exception {

        doNothing().when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/order/remove/1"))
                .andExpect(status().isNoContent());

        verify(orderService).deleteOrderById(1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void deleteOrder_whenOrderNotFound_shouldReturn404() throws Exception {

        doThrow(new OrderNotFoundException(1L))
                .when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/order/remove/1"))
                .andExpect(status().isNotFound());

        verify(orderService).deleteOrderById(1L);
    }
}
