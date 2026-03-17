package com.prashant.backendorderservice.orders.dto.request;

import com.prashant.backendorderservice.orders.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateOrderStatusRequest {

    @Schema(
            description = "Status of ordered item",
            example = "PROCESSING"
    )
    @NotNull(message = "Status is required")
    private OrderStatus status;

}
