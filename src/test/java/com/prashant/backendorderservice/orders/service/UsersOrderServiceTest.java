package com.prashant.backendorderservice.orders.service;

import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.entity.Order;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UsersOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthUtil authUtil;


    @InjectMocks
    private UsersOrderService usersOrderService;

    @BeforeEach
    void setUp() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(authUtil.getAuthenticatedUser()).thenReturn(mockUser);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(123L);
        request.setDescription("Test Order");

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId(123L);
        savedOrder.setUserId(1L);
        savedOrder.setDescription("Test Order");
        savedOrder.setStatus(OrderStatus.CREATED);

        User mockUser = new User();
        mockUser.setId(1L);

        when(authUtil.getAuthenticatedUser()).thenReturn(mockUser);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        // Act
        OrderResponse orderResponse = usersOrderService.createOrder(request);

        // Assert
        assertNotNull(orderResponse);
        assertEquals(1L, orderResponse.getId());
        assertEquals(123L, orderResponse.getCustomerId());
        assertEquals("Test Order", orderResponse.getDescription());
        assertEquals(OrderStatus.CREATED, orderResponse.getStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

}
