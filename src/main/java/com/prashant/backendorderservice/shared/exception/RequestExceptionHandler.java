package com.prashant.backendorderservice.shared.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@RestControllerAdvice
@Order(30)
public class RequestExceptionHandler {

    record ErrorEntry(Predicate<Throwable> condition, String error, String message) {}

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.error("Invalid request body: path={}, message={}",
                request.getRequestURI(),
                ex.getMessage());

        Throwable cause = ex.getCause();


        List<ErrorEntry> errorMappings = List.of(
                new ErrorEntry(
                        c -> c instanceof JsonParseException,
                        "INVALID_JSON",
                        "Request body contains malformed JSON"
                ),
                new ErrorEntry(
                        c -> c instanceof MismatchedInputException,
                        "INVALID_FIELD_TYPE",
                        "One or more fields have incorrect data type"
                )
        );

        ErrorEntry matched = errorMappings.stream()
                .filter(entry -> cause != null && entry.condition().test(cause))
                .findFirst()
                .orElse(new ErrorEntry(
                        null,
                        "UNREADABLE_REQUEST",
                        "Request body could not be read"
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(matched.error())
                        .message(matched.message())
                        .path(request.getRequestURI())
                        .build());
    }
}