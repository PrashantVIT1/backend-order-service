package com.prashant.backendorderservice.shared.config.swagger;

import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface GlobalExceptionHandlerDocs {

    @Operation(summary = "Unknown Internal Server Error")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 500,
                                  "error": "INTERNAL_SERVER_ERROR",
                                  "message": "An unknown error occurred",
                                  "path": "/"
                                }
                                """)
                    )
            )}
    )
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request);
}
