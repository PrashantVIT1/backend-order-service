package com.prashant.backendorderservice.auth.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "praj2")
    @NotBlank(message = "Username must not be blank")
    private String username;
    @Schema(example = "best_password_ever123")
    @NotBlank(message = "Password must not be blank")
    private String password;
}
