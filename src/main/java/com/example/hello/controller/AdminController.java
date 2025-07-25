package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.dto.LoginRequest;
import com.example.hello.dto.LoginResponse;
import com.example.hello.dto.PasswordChangeRequest;
import com.example.hello.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = adminService.login(loginRequest);
        return ApiResponse.success(response);
    }
    
    @PutMapping("/password")
    public ApiResponse<?> changePassword(
            Authentication authentication,
            @RequestBody PasswordChangeRequest request
    ) {
        String username = authentication.getName();
        adminService.changePassword(username, request);
        return ApiResponse.success(null, "密码修改成功");
    }
} 