package com.example.hello.controller;

import com.example.hello.dto.ApiResponse;
import com.example.hello.dto.ContactRequest;
import com.example.hello.dto.ContactResponse;
import com.example.hello.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private ContactService contactService;
    
    @GetMapping("/contact")
    public ApiResponse<ContactResponse> getContact() {
        ContactResponse contact = contactService.getContactInfo();
        return ApiResponse.success(contact);
    }
    
    @PutMapping("/admin/contact")
    public ApiResponse<ContactResponse> updateContact(@RequestBody ContactRequest contactRequest) {
        ContactResponse contact = contactService.updateContact(contactRequest);
        return ApiResponse.success(contact);
    }
} 