package com.prashant.backendorderservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.prashant.backendorderservice.model.OrderStatus.CREATED;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @PrePersist
    public void onCreate() {
        if (status == null) {
            status = CREATED;
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Id
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}