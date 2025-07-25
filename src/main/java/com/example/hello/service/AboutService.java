package com.example.hello.service;

import com.example.hello.model.About;
import com.example.hello.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AboutService {

    @Autowired
    private AboutRepository aboutRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public About getAboutInfo() {
        List<About> aboutList = aboutRepository.findAll();
        if (aboutList.isEmpty()) {
            return About.builder()
                    .title("关于我们")
                    .content("公司简介")
                    .image("/uploads/default-about.jpg")
                    .build();
        }
        return aboutList.get(0);
    }
    
    public About updateAbout(String title, String content, MultipartFile file) throws IOException {
        List<About> aboutList = aboutRepository.findAll();
        About about;
        
        if (aboutList.isEmpty()) {
            about = new About();
        } else {
            about = aboutList.get(0);
        }
        
        about.setTitle(title);
        about.setContent(content);
        
        if (file != null && !file.isEmpty()) {
            if (about.getImage() != null && !about.getImage().equals("/uploads/default-about.jpg")) {
                fileStorageService.deleteFile(about.getImage());
            }
            String imageUrl = fileStorageService.storeFile(file);
            about.setImage(imageUrl);
        } else if (about.getImage() == null) {
            about.setImage("/uploads/default-about.jpg");
        }
        
        return aboutRepository.save(about);
    }
} 