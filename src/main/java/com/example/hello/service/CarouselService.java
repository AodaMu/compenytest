package com.example.hello.service;

import com.example.hello.model.Carousel;
import com.example.hello.repository.CarouselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CarouselService {

    @Autowired
    private CarouselRepository carouselRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public List<Carousel> getAllCarousels() {
        return carouselRepository.findAll();
    }
    
    public Carousel getCarousel(Long id) {
        return carouselRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("轮播图不存在"));
    }
    
    public Carousel createCarousel(String title, String link, MultipartFile file) throws IOException {
        String imageUrl = fileStorageService.storeFile(file);
        
        Carousel carousel = Carousel.builder()
                .title(title)
                .image(imageUrl)
                .link(link)
                .build();
                
        return carouselRepository.save(carousel);
    }
    
    public Carousel updateCarousel(Long id, String title, String link, MultipartFile file) throws IOException {
        Carousel carousel = carouselRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("轮播图不存在"));
        
        carousel.setTitle(title);
        carousel.setLink(link);
        
        if (file != null && !file.isEmpty()) {
            // 删除旧文件
            fileStorageService.deleteFile(carousel.getImage());
            // 保存新文件
            String imageUrl = fileStorageService.storeFile(file);
            carousel.setImage(imageUrl);
        }
        
        return carouselRepository.save(carousel);
    }
    
    public void deleteCarousel(Long id) throws IOException {
        Carousel carousel = carouselRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("轮播图不存在"));
        
        // 删除文件
        fileStorageService.deleteFile(carousel.getImage());
        
        // 删除数据
        carouselRepository.deleteById(id);
    }
} 