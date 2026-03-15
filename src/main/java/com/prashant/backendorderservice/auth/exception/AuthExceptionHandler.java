package com.prashant.backendorderservice.auth.exception;

import com.prashant.backendorderservice.shared.config.exception.ExceptionHandlerOrder;
import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.AUTH_HANDLER_ORDER)
public class AuthExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex, HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
            UserAlreadyExistsException ex, HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("USER_ALREADY_EXISTS")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(
            JwtException ex, HttpServletRequest request) {

        boolean isExpired = ex instanceof ExpiredJwtException;

        log.warn("JWT error: path={}, type={}, message={}",
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(isExpired ? "TOKEN_EXPIRED" : "INVALID_TOKEN")
                .message(isExpired ? "Session expired, please login again" : "Invalid or malformed token")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException( HttpServletRequest request) {

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("ACCESS_DENIED")
                .message("Access denied: Insufficient permissions")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
