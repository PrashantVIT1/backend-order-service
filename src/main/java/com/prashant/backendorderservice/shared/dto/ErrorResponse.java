package com.prashant.backendorderservice.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    @Schema(
            description = "Timestamp when the error occurred",
            example = "2026-02-05T06:01:28.829"
    )
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @Schema(
            description = "HTTP status code",
            example = "404"
    )
    private int status;

    @Schema(
            description = "Application-level error code",
            example = "RESOURCE_NOT_FOUND"
    )
    private String error;

    @Schema(
            description = "Human-readable error message",
            example = "The requested resource could not be found"
    )
    private String message;

    @Schema(
            description = "Request path that triggered the error",
            example = "/api/v1/orders/32"
    )
    private String path;
}

