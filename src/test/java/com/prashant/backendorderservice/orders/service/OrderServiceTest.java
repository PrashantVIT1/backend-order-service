package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.orders.entity.Order;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.exception.custom.OrderNotFoundException;
import com.prashant.backendorderservice.orders.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthUtil authUtil;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        UpdateOrderStatusResponse response = orderService.updateOrderStatusById(1L, request);

        assertEquals(OrderStatus.SHIPPED, response.getStatus());
        verify(orderRepository).save(order);
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    @Test
    void shouldReturnOrderWhenFound() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(123L);
        order.setDescription("Test Order");
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        OrderResponse response = orderService.getOrderById(1L);

        assertEquals(1L, response.getId());
        assertEquals(OrderStatus.CREATED, response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void shouldDeleteOrderWhenExists() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.deleteOrderById(1L);

        verify(orderRepository).delete(order);
    }
}