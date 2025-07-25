package com.example.hello.service;

import com.example.hello.dto.LoginRequest;
import com.example.hello.dto.LoginResponse;
import com.example.hello.dto.PasswordChangeRequest;
import com.example.hello.model.Admin;
import com.example.hello.repository.AdminRepository;
import com.example.hello.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostConstruct
    public void init() {
        // 初始化默认管理员账户
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = Admin.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .build();
            adminRepository.save(admin);
        }
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        
        return LoginResponse.builder()
                .token(token)
                .username(loginRequest.getUsername())
                .build();
    }
    
    public void changePassword(String username, PasswordChangeRequest request) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("管理员不存在"));
        
        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }
        
        // 更新密码
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);
    }
} 