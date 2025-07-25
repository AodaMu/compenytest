package com.example.hello.repository;

import com.example.hello.model.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface AboutRepository extends JpaRepository<About, Long> {
} 