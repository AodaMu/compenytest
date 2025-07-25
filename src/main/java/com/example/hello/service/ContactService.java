package com.example.hello.service;

import com.example.hello.dto.ContactRequest;
import com.example.hello.dto.ContactResponse;
import com.example.hello.model.Contact;
import com.example.hello.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    
    public ContactResponse getContactInfo() {
        List<Contact> contactList = contactRepository.findAll();
        
        if (contactList.isEmpty()) {
            return ContactResponse.builder()
                    .address("公司地址")
                    .phone("联系电话")
                    .email("电子邮箱")
                    .workTime("工作时间")
                    .mapLocation(new ContactResponse.MapLocation(31.2304, 121.4737))
                    .socialMedia(new ContactResponse.SocialMedia("", "", ""))
                    .build();
        }
        
        Contact contact = contactList.get(0);
        return ContactResponse.builder()
                .address(contact.getAddress())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .workTime(contact.getWorkTime())
                .mapLocation(new ContactResponse.MapLocation(contact.getLat(), contact.getLng()))
                .socialMedia(new ContactResponse.SocialMedia(
                        contact.getWeibo(),
                        contact.getWechat(),
                        contact.getLinkedin()
                ))
                .build();
    }
    
    public ContactResponse updateContact(ContactRequest contactRequest) {
        List<Contact> contactList = contactRepository.findAll();
        Contact contact;
        
        if (contactList.isEmpty()) {
            contact = new Contact();
        } else {
            contact = contactList.get(0);
        }
        
        contact.setAddress(contactRequest.getAddress());
        contact.setPhone(contactRequest.getPhone());
        contact.setEmail(contactRequest.getEmail());
        contact.setWorkTime(contactRequest.getWorkTime());
        
        if (contactRequest.getMapLocation() != null) {
            contact.setLat(contactRequest.getMapLocation().getLat());
            contact.setLng(contactRequest.getMapLocation().getLng());
        }
        
        if (contactRequest.getSocialMedia() != null) {
            contact.setWeibo(contactRequest.getSocialMedia().getWeibo());
            contact.setWechat(contactRequest.getSocialMedia().getWechat());
            contact.setLinkedin(contactRequest.getSocialMedia().getLinkedin());
        }
        
        Contact savedContact = contactRepository.save(contact);
        
        return ContactResponse.builder()
                .address(savedContact.getAddress())
                .phone(savedContact.getPhone())
                .email(savedContact.getEmail())
                .workTime(savedContact.getWorkTime())
                .mapLocation(new ContactResponse.MapLocation(savedContact.getLat(), savedContact.getLng()))
                .socialMedia(new ContactResponse.SocialMedia(
                        savedContact.getWeibo(),
                        savedContact.getWechat(),
                        savedContact.getLinkedin()
                ))
                .build();
    }
} 