package com.prashant.backendorderservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
    @Schema(example = "2026-02-05T06:01:28.829Z")
    private LocalDateTime timestamp;
    @Schema(example = "404")
    private int status;
    @Schema(example = "ORDER_NOT_FOUND")
    private String error;
    @Schema(example = "Order not found with id: 32")
    private String message;
    @Schema(example = "/order/remove/32")
    private String path;
}

