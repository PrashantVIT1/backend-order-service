package com.prashant.backendorderservice.auth.config.swagger;

import com.prashant.backendorderservice.auth.dto.request.LoginRequest;
import com.prashant.backendorderservice.auth.dto.response.LoginResponse;
import com.prashant.backendorderservice.auth.dto.response.SignupResponse;
import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name = "Authentication", description = "Authentication APIs")
public interface AuthControllerDocs {
    // ================= USER LOGIN =================
    @Operation(summary = "Login an existing user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User login successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFqOTAiLCJ1c2VySWQiOiIyMyIsImlhdCI6MTc3MzY2MDQxNSwiZXhwIjoxNzczNjYxMDE1fQ.JAT5vBunKM8BoKyYsHLfKbRyQM5h-zA0-gqAp6EGjYifx8dAc2yR4Wk5g1J8E8wCFrNV7Q3CifnjQdxgRsPRaQ",
                                  "userId": 23
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Empty Username/Password field",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 400,
                                  "error": "VALIDATION_ERROR",
                                  "message": "[password: Password must not be blank, username: Username must not be blank]",
                                  "path": "/auth/signup"
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "timestamp": "2026-03-16T17:46:35.114979100",
                                       "status": 401,
                                       "error": "UNAUTHORIZED",
                                       "message": "Invalid username or password",
                                       "path": "/orders"
                                     }
                                """)
                    )
            )
    })
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    // ================= USER SIGNUP =================
    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "id": "16",
                                  "username": "praj10"
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Empty Username/Password field",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 400,
                                  "error": "VALIDATION_ERROR",
                                  "message": "[password: Password must not be blank, username: Username must not be blank]",
                                  "path": "/auth/signup"
                                }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Use Different Username",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 409,
                                  "error": "USER_ALREADY_EXISTS",
                                  "message": "User already exists: praj10",
                                  "path": "/auth/signup"
                                }
                                """)
                    )
            )
    })
    ResponseEntity<SignupResponse> signup(LoginRequest signupRequest);


}
