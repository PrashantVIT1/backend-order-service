package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.dto.response.OrderResponse;
import com.prashant.backendorderservice.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.model.OrderStatus;
import com.prashant.backendorderservice.repository.OrderRepository;

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
    @InjectMocks
    private OrderService orderService;



    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(123L);
        request.setDescription("Test Order");

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId(123L);
        savedOrder.setDescription("Test Order");

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        // Act
        Long orderId = orderService.createOrder(request);

        // Assert
        assertNotNull(orderId);
        assertEquals(1L, orderId);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        UpdateOrderStatusResponse response = orderService.updateOrderStatusbyId(1L, request);

        assertEquals("SHIPPED", response.getStatus());
        verify(orderRepository).save(order);
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
        assertEquals("CREATED", response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.getOrderById(1L)
        );

        assertTrue(exception.getMessage().contains("Order not found"));
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
