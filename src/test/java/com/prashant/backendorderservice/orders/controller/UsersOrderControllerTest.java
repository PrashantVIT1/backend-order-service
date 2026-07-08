package com.prashant.backendorderservice.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.backendorderservice.auth.config.OAuth2SuccessHandler;
import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.filter.JwtAuthFilter;
import com.prashant.backendorderservice.auth.support.SecuredControllerTest;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.service.UsersOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsersOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class UsersOrderControllerTest extends SecuredControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersOrderService usersOrderService;


    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private OAuth2SuccessHandler oAuth2SuccessHandler;


    @MockBean
    private AuthenticationProvider authenticationProvider;

    // ================= CREATE ORDER =================

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setDescription("Laptop");
        request.setCustomerId(1L);

        OrderResponse response = OrderResponse.builder()
                .id(1L)
                .customerId(1L)
                .description("Laptop")
                .status(OrderStatus.CREATED).build();

        User mockUser = new User();
        mockUser.setId(1L);

        when(usersOrderService.createOrder(any()))
                .thenReturn(response);

        mockMvc.perform(post("/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.description").value("Laptop"))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(usersOrderService).createOrder(any(CreateOrderRequest.class));
        verifyNoMoreInteractions(usersOrderService);
    }
    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        // missing required fields

        mockMvc.perform(post("/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
