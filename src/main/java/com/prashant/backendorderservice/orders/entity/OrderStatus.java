package com.prashant.backendorderservice.orders.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.prashant.backendorderservice.orders.serializer.OrderStatusDeserializer;

@JsonDeserialize(using = OrderStatusDeserializer.class)
public enum OrderStatus {
    CREATED,
    PROCESSING,
    SHIPPED,
    COMPLETED,
    CANCELLED
}
