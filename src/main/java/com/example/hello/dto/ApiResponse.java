package com.example.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private T data;
    private String message;
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(200)
                .data(data)
                .message(message)
                .build();
    }
    
    public static ApiResponse<?> error(int code, String message) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
} 