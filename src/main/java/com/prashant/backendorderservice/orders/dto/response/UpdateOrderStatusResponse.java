package com.prashant.backendorderservice.orders.dto.response;

import com.prashant.backendorderservice.orders.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateOrderStatusResponse {

    @Schema(
            description = "Ordered item Id",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Status of ordered item",
            example = "PROCESSING"
    )
    private OrderStatus status;
    @Schema(
            description = "Timestamp when the orders updated",
            example = "PROCESSING"
    )
    private LocalDateTime updatedAt;
}
