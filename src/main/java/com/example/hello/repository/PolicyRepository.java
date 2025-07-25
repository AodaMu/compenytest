package com.example.hello.repository;

import com.example.hello.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
} 