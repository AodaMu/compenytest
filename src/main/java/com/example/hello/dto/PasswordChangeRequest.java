package com.example.hello.dto;

import lombok.Data;
 
@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
} 