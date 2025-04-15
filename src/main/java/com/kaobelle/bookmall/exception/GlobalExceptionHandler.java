package com.kaobelle.bookmall.exception;

import com.kaobelle.bookmall.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Object> errorResponse = ApiResponse.error(ex.getReason());

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
}
