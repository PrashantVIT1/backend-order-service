package com.prashant.backendorderservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @Schema(example = "123")
    @NotNull
    private Long customerId;

    @Schema(example = "iPhone 15 Pro")
    @NotBlank
    private String description;
}
