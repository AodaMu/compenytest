package com.example.hello.service;

import com.example.hello.model.Policy;
import com.example.hello.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }
    
    public Policy getPolicy(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("优惠政策不存在"));
    }
    
    public Policy createPolicy(String title, String content, String detailContent, MultipartFile file) throws IOException {
        String imageUrl = fileStorageService.storeFile(file);
        
        Policy policy = Policy.builder()
                .title(title)
                .content(content)
                .detailContent(detailContent)
                .image(imageUrl)
                .build();
                
        return policyRepository.save(policy);
    }
    
    public Policy updatePolicy(Long id, String title, String content, String detailContent, MultipartFile file) throws IOException {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("优惠政策不存在"));
        
        policy.setTitle(title);
        policy.setContent(content);
        policy.setDetailContent(detailContent);
        
        if (file != null && !file.isEmpty()) {
            // 删除旧文件
            fileStorageService.deleteFile(policy.getImage());
            // 保存新文件
            String imageUrl = fileStorageService.storeFile(file);
            policy.setImage(imageUrl);
        }
        
        return policyRepository.save(policy);
    }
    
    public void deletePolicy(Long id) throws IOException {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("优惠政策不存在"));
        
        // 删除文件
        fileStorageService.deleteFile(policy.getImage());
        
        // 删除数据
        policyRepository.deleteById(id);
    }
} 