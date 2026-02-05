package com.prashant.backendorderservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateOrderStatusResponse {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "PROCESSING")
    private String status;
    @Schema(example = "2026-02-05T06:01:28.829Z")
    private LocalDateTime updatedAt;
}
