package com.kaobelle.bookmall.exception;

import com.kaobelle.bookmall.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        ApiResponse<Object> errorResponse = ApiResponse.error(ex.getReason());

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // 擷取所有欄位錯誤訊息
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        // 也可以只取第一筆錯誤訊息
        String message = errorMessages.isEmpty() ? "Invalid input" : errorMessages.get(0);
        ApiResponse<Object> errorResponse = ApiResponse.error(message);

        // 回傳錯誤訊息陣列 + 一個固定總結訊息
        // ApiResponse<Object> errorResponse = new ApiResponse<>(false, "資料驗證失敗", errorMessages);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
