package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.model.About;
import com.example.hello.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AboutController {

    @Autowired
    private AboutService aboutService;
    
    @GetMapping("/about")
    public ApiResponse<About> getAbout() {
        About about = aboutService.getAboutInfo();
        return ApiResponse.success(about);
    }
    
    @PutMapping("/admin/about")
    public ApiResponse<About> updateAbout(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        About about = aboutService.updateAbout(title, content, file);
        return ApiResponse.success(about);
    }
} 