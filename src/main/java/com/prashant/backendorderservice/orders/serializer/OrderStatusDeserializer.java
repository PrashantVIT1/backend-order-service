package com.prashant.backendorderservice.orders.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.prashant.backendorderservice.orders.entity.OrderStatus;
import com.prashant.backendorderservice.orders.exception.custom.OrderStatusInvalidException;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OrderStatusDeserializer extends JsonDeserializer<OrderStatus> {

    @Override
    public OrderStatus deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException {

        String value = p.getText();

        if (value == null || value.isBlank()) {
            return OrderStatus.CREATED;  // default
        }

        try {
            return OrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new OrderStatusInvalidException(  // your custom exception
                    "Invalid status value: " + value + ". Valid values: "
                            + Arrays.stream(OrderStatus.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", "))
            );
        }
    }
}
