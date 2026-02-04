package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

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

}
