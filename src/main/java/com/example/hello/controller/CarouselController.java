package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.model.Carousel;
import com.example.hello.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    
    @GetMapping("/carousel")
    public ApiResponse<List<Carousel>> getAllCarousels() {
        List<Carousel> carousels = carouselService.getAllCarousels();
        return ApiResponse.success(carousels);
    }
    
    @GetMapping("/admin/carousel")
    public ApiResponse<List<Carousel>> getAdminCarousels() {
        List<Carousel> carousels = carouselService.getAllCarousels();
        return ApiResponse.success(carousels);
    }
    
    @PostMapping("/admin/carousel")
    public ApiResponse<Carousel> createCarousel(
            @RequestParam("title") String title,
            @RequestParam("link") String link,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Carousel carousel = carouselService.createCarousel(title, link, file);
        return ApiResponse.success(carousel);
    }
    
    @PutMapping("/admin/carousel/{id}")
    public ApiResponse<Carousel> updateCarousel(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("link") String link,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        Carousel carousel = carouselService.updateCarousel(id, title, link, file);
        return ApiResponse.success(carousel);
    }
    
    @DeleteMapping("/admin/carousel/{id}")
    public ApiResponse<?> deleteCarousel(@PathVariable Long id) throws IOException {
        carouselService.deleteCarousel(id);
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return ApiResponse.success(data);
    }
} 