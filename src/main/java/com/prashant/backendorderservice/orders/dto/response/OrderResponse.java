package com.prashant.backendorderservice.orders.dto.response;

import com.prashant.backendorderservice.orders.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
    @Schema(
            description = "Ordered item Id",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "Id associated with customer",
            example = "123"
    )
    private Long customerId;

    @Schema(
            description = "Description of ordered item",
            example = "iPhone 15 Pro"
    )
    private String description;
    @Schema(
            description = "Status of ordered item",
            example = "PROCESSING"
    )
    private OrderStatus status;
}
