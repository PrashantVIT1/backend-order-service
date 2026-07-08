package com.prashant.backendorderservice.auth.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    @Schema(
            description = "Unique username to login/signup",
            example = "praj2"
    )
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Schema(
            description = "Password to login/signup",
            example = "best_password_ever123"
    )
    @NotBlank(message = "Password must not be blank")
    private String password;
}
