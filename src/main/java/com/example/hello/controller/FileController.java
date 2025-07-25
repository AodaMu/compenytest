package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/upload")
    public ApiResponse<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.storeFile(file);
        
        Map<String, String> data = new HashMap<>();
        data.put("url", fileUrl);
        
        return ApiResponse.success(data);
    }
} 