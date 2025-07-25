package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.service.LogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class LogoController {

    @Autowired
    private LogoService logoService;
    
    @GetMapping("/logo")
    public ApiResponse<String> getLogo() {
        String logoUrl = logoService.getLogo();
        return ApiResponse.success(logoUrl);
    }
    
    @PutMapping("/admin/logo")
    public ApiResponse<String> updateLogo(@RequestParam("file") MultipartFile file) throws IOException {
        String logoUrl = logoService.updateLogo(file);
        return ApiResponse.success(logoUrl);
    }
} 