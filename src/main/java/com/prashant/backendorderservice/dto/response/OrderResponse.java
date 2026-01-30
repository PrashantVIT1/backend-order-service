package com.prashant.backendorderservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String description;
    private String status;
}
