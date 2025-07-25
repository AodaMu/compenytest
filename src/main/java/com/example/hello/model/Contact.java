package com.example.hello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String workTime;
    
    @Column(nullable = false)
    private Double lat;
    
    @Column(nullable = false)
    private Double lng;
    
    @Column
    private String weibo;
    
    @Column
    private String wechat;
    
    @Column
    private String linkedin;
} 