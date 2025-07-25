package com.example.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private String address;
    private String phone;
    private String email;
    private String workTime;
    
    private MapLocation mapLocation;
    private SocialMedia socialMedia;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MapLocation {
        private Double lat;
        private Double lng;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocialMedia {
        private String weibo;
        private String wechat;
        private String linkedin;
    }
} 