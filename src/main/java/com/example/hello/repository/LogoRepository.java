package com.example.hello.repository;

import com.example.hello.model.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {
    Logo findFirstByOrderById();
} 