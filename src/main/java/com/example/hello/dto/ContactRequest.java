package com.example.hello.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ContactRequest {
    private String address;
    private String phone;
    private String email;
    private String workTime;
    
    private MapLocation mapLocation;
    private SocialMedia socialMedia;
    
    @Data
    public static class MapLocation {
        private Double lat;
        private Double lng;
    }
    
    @Data
    public static class SocialMedia {
        private String weibo;
        private String wechat;
        private String linkedin;
    }
} 