package com.prashant.backendorderservice.dto.request;

import com.prashant.backendorderservice.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateOrderStatusRequest {

    @Schema(example = "PROCESSING")
    @NotNull(message = "Status is required")
    private OrderStatus status;

}
