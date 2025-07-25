package com.example.hello.repository;

import com.example.hello.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
} 