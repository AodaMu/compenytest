package com.example.hello.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    
    @Value("${file.upload.dir}")
    private String uploadDir;
    
    public String storeFile(MultipartFile file) throws IOException {
        // 创建上传目录
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // 生成唯一文件名
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetPath = Paths.get(uploadDir).resolve(fileName);
        
        // 保存文件
        Files.copy(file.getInputStream(), targetPath);
        
        // 返回文件访问URL (使用绝对路径)
        return "/uploads/" + fileName;
    }
    
    public void deleteFile(String fileUrl) throws IOException {
        if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
            String fileName = fileUrl.substring("/uploads/".length());
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(targetPath);
        }
    }
} 