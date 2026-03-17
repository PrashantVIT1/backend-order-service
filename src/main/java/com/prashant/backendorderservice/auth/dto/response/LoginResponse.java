package com.prashant.backendorderservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @Schema(
            description = "JWT token for Authorization",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFqOTAiLCJ1c2VySWQiOiIyMyIsImlhdCI6MTc3MzY2MDQxNSwiZXhwIjoxNzczNjYxMDE1fQ.JAT5vBunKM8BoKyYsHLfKbRyQM5h-zA0-gqAp6EGjYifx8dAc2yR4Wk5g1J8E8wCFrNV7Q3CifnjQdxgRsPRaQ"
    )
    String token;
    @Schema(
            description = "Id associated with username",
            example = "23"
    )
    Long userId;
}
