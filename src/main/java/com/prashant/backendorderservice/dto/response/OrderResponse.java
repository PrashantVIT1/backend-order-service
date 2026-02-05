package com.prashant.backendorderservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "123")
    private Long customerId;
    @Schema(example = "iPhone 15 Pro")
    private String description;
    @Schema(example = "CREATED")
    private String status;
}
