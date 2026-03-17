package com.prashant.backendorderservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    @Schema(
            description = "Id associated with username",
            example = "23"
    )
    private long id;
    @Schema(
            description = "Unique username to signup",
            example = "praj2"
    )
    private String username;
}
