package com.prashant.backendorderservice.dto.response;

import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateOrderStatusResponse {

    private Long id;
    private String status;
    private LocalDateTime updatedAt;
}
