package com.prashant.backendorderservice.shared.exception;

import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)  // always last resort
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error: path={}, message={}",
                request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("INTERNAL_ERROR")
                        .message("Something went wrong")
                        .path(request.getRequestURI())
                        .build());
    }
}
