package com.prashant.backendorderservice.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.backendorderservice.auth.config.WebSecurityConfig;
import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.orders.exception.custom.OrderNotFoundException;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.service.OrderService;
import com.prashant.backendorderservice.auth.support.SecuredControllerTest;
import com.prashant.backendorderservice.orders.service.UsersOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(WebSecurityConfig.class)
@WithMockUser
class OrderControllerTest extends SecuredControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UsersOrderService  usersOrderService;

    @Autowired
    private ObjectMapper objectMapper;

//     ================= UPDATE STATUS =================

    @Test
    void shouldUpdateOrderStatusSuccessfully() throws Exception {

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.SHIPPED);

        UpdateOrderStatusResponse response = UpdateOrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.SHIPPED)
                .updatedAt(LocalDateTime.now())
                .build();

        when(orderService.updateOrderStatusById(
                eq(1L),
                any(UpdateOrderStatusRequest.class)
        )).thenReturn(response);

        mockMvc.perform(patch("/admin/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("SHIPPED"));

        verify(orderService).updateOrderStatusById(eq(1L), any(UpdateOrderStatusRequest.class));
        verifyNoMoreInteractions(orderService);
    }


    // ================= GET ORDER =================

@Test
void shouldReturnOrderWhenExists() throws Exception {

    OrderResponse response = OrderResponse.builder()
            .id(1L)
            .customerId(1L)
            .description("Laptop")
            .status(OrderStatus.SHIPPED).build();


    when(orderService.getOrderById(1L))
            .thenReturn(response);

    mockMvc.perform(get("/admin/orders/1"))
            .andExpect(status().isOk())
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

        mockMvc.perform(get("/admin/orders/1"))
                .andExpect(status().isNotFound());

        verify(orderService).getOrderById(1L);
        verifyNoMoreInteractions(orderService);
    }

//    // ================= DELETE ORDER =================

    @Test
    void shouldDeleteOrderSuccessfully() throws Exception {

        doNothing().when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/admin/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService).deleteOrderById(1L);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void deleteOrder_whenOrderNotFound_shouldReturn404() throws Exception {

        doThrow(new OrderNotFoundException(1L))
                .when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/admin/orders/1"))
                .andExpect(status().isNotFound());

        verify(orderService).deleteOrderById(1L);
    }
}
