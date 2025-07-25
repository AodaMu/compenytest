package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.model.Policy;
import com.example.hello.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    
    @GetMapping("/policies")
    public ApiResponse<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return ApiResponse.success(policies);
    }
    
    @GetMapping("/admin/policies")
    public ApiResponse<List<Policy>> getAdminPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return ApiResponse.success(policies);
    }
    
    @PostMapping("/admin/policies")
    public ApiResponse<Policy> createPolicy(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("detailContent") String detailContent,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Policy policy = policyService.createPolicy(title, content, detailContent, file);
        return ApiResponse.success(policy);
    }
    
    @PutMapping("/admin/policies/{id}")
    public ApiResponse<Policy> updatePolicy(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("detailContent") String detailContent,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        Policy policy = policyService.updatePolicy(id, title, content, detailContent, file);
        return ApiResponse.success(policy);
    }
    
    @DeleteMapping("/admin/policies/{id}")
    public ApiResponse<?> deletePolicy(@PathVariable Long id) throws IOException {
        policyService.deletePolicy(id);
        return ApiResponse.success(null, "删除成功");
    }
} 