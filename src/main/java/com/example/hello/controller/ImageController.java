package com.example.hello.controller;

import com.example.hello.model.ImageData;
import com.example.hello.service.ImageStorageService;
import com.example.hello.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageName = imageStorageService.storeImage(file);
            
            // 构建完整的URL
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/")
                .path(imageName)
                .toUriString();
                
            Map<String, String> data = new HashMap<>();
            data.put("name", imageName);
            data.put("url", imageUrl);
            
            return ResponseEntity.ok(new ApiResponse(200, data, null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, null, "图片上传失败：" + e.getMessage()));
        }
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        ImageData image = imageStorageService.getImageByName(imageName);
        if (image != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(image.getType()))
                    .body(image.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{imageName}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable String imageName) {
        imageStorageService.deleteImage(imageName);
        return ResponseEntity.ok(new ApiResponse(200, null, "图片删除成功"));
    }
} 