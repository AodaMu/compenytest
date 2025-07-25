package com.example.hello.service;

import com.example.hello.model.ImageData;
import com.example.hello.repository.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Autowired
    private ImageDataRepository imageDataRepository;
    
    public String storeImage(MultipartFile file) throws IOException {
        // 生成唯一图片名称
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        
        ImageData imageData = ImageData.builder()
                .name(fileName)
                .type(file.getContentType())
                .imageData(file.getBytes())
                .build();
        
        imageDataRepository.save(imageData);
        
        // 返回图片名称
        return fileName;
    }
    
    public ImageData getImageByName(String name) {
        Optional<ImageData> imageData = imageDataRepository.findByName(name);
        return imageData.orElse(null);
    }
    
    public void deleteImage(String name) {
        Optional<ImageData> imageData = imageDataRepository.findByName(name);
        imageData.ifPresent(imageDataRepository::delete);
    }
} 