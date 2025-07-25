package com.example.hello.service;

import com.example.hello.model.Logo;
import com.example.hello.repository.LogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LogoService {

    @Autowired
    private LogoRepository logoRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public String getLogo() {
        Logo logo = logoRepository.findFirstByOrderById();
        if (logo == null) {
            return "/uploads/default-logo.png";
        }
        return logo.getUrl();
    }
    
    public String updateLogo(MultipartFile file) throws IOException {
        Logo logo = logoRepository.findFirstByOrderById();
        
        if (logo == null) {
            logo = new Logo();
        } else if (logo.getUrl() != null && !logo.getUrl().equals("/uploads/default-logo.png")) {
            fileStorageService.deleteFile(logo.getUrl());
        }
        
        String logoUrl = fileStorageService.storeFile(file);
        logo.setUrl(logoUrl);
        
        logoRepository.save(logo);
        
        return logoUrl;
    }
} 