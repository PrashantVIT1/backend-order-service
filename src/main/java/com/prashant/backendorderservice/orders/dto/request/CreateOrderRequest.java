package com.prashant.backendorderservice.orders.dto.request;

import com.prashant.backendorderservice.orders.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @Schema(
            description = "Id associated with customer",
            example = "123"
    )
    @NotNull
    private Long customerId;
    @Schema(
            description = "Description of ordered item",
            example = "iPhone 15 Pro"
    )
    @NotBlank
    private String description;
    @Schema(
            description = "Status of ordered item",
            example = "CREATED"
    )
    private OrderStatus status;
}
