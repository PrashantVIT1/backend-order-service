package com.prashant.backendorderservice.integration;


import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.model.OrderStatus;
import com.prashant.backendorderservice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


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

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldSaveOrder() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDescription("MacBook");

        Order saved = orderRepository.saveAndFlush(order);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindOrderById() {

        Order order = new Order();
        order.setCustomerId(1L);
        order.setDescription("MacBook");

        Order saved = orderRepository.saveAndFlush(order);

        entityManager.clear(); // force DB read

        Order found = orderRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getId()).isEqualTo(saved.getId());
    }


    @Test
    void shouldUpdateOrderStatusById() {

        Order order = new Order();
        order.setCustomerId(1L);
        order.setDescription("MacBook");

        Order saved = orderRepository.saveAndFlush(order);

        saved.setStatus(OrderStatus.CANCELLED);
        orderRepository.saveAndFlush(saved);

        Order updated = orderRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(updated.getStatus())
                .isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void shouldDeleteOrderById() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setDescription("MacBook");
        Order saved = orderRepository.saveAndFlush(order);
        orderRepository.deleteById(saved.getId());
        orderRepository.flush();

        assertThat(orderRepository.findById(saved.getId()))
                .isNotPresent();
    }

    @Test
    void shouldFindAllOrders() {

        // Arrange
        Order order1 = new Order();
        order1.setCustomerId(1L);
        order1.setDescription("MacBook");

        Order order2 = new Order();
        order2.setCustomerId(2L);
        order2.setDescription("Books");

        orderRepository.saveAllAndFlush(List.of(order1, order2));

        entityManager.clear(); // force DB read

        // Act
        List<Order> found = orderRepository.findAll();

        // Assert
        assertThat(found).hasSize(2)
                .extracting(Order::getDescription)
                .containsExactlyInAnyOrder("MacBook", "Books");
    }
    

}

