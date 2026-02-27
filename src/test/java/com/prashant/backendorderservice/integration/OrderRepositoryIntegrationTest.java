package com.prashant.backendorderservice.integration;

import com.prashant.backendorderservice.exception.OrderNotFoundException;
import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");



    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveOrder() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDescription("MacBook");

        Order saved = orderRepository.save(order);
        assertThat(saved.getId()).isNotNull();
    }

}

