package com.prashant.backendorderservice.orders.repository;

import com.prashant.backendorderservice.order.entity.Order;
import com.prashant.backendorderservice.order.entity.OrderStatus;
import com.prashant.backendorderservice.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should save and retrieve order successfully")
    void shouldSaveAndFindOrder() {

        // Given
        Order order = new Order();
        order.setCustomerId(123L);
        order.setDescription("iPhone 15");
        order.setStatus(OrderStatus.CREATED);

        // When
        Order savedOrder = orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        // Then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getCustomerId()).isEqualTo(123L);
        assertThat(foundOrder.get().getDescription()).isEqualTo("iPhone 15");
        assertThat(foundOrder.get().getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    @DisplayName("Should return all orders")
    void shouldReturnAllOrders() {


        Order order1 = new Order();
        order1.setCustomerId(123L);
        order1.setDescription("iPhone 15");
        order1.setStatus(OrderStatus.CREATED);

        Order order2 = new Order();
        order2.setCustomerId(2L);
        order2.setDescription("Mobile");
        order2.setStatus(OrderStatus.CREATED);

        orderRepository.save(order1);
        orderRepository.save(order2);

        assertThat(orderRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Should delete all orders")
    void shouldDeleteAllOrders() {


        Order order1 = new Order();
        order1.setCustomerId(123L);
        order1.setDescription("iPhone 15");
        order1.setStatus(OrderStatus.CREATED);

        Order order2 = new Order();
        order2.setCustomerId(2L);
        order2.setDescription("Mobile");
        order2.setStatus(OrderStatus.CREATED);

        orderRepository.saveAll(List.of(order1, order2));
        orderRepository.flush();

        assertThat(orderRepository.findAll()).hasSize(2);

        orderRepository.deleteAll();

        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Test
    void shouldFailWhenDescriptionIsNull() {
        Order order = new Order();
        order.setCustomerId(123L);
        order.setDescription(null);
        order.setStatus(OrderStatus.CREATED);

        orderRepository.save(order);

        assertThatThrownBy(() -> orderRepository.flush())
                .isInstanceOf(Exception.class);
    }



}
