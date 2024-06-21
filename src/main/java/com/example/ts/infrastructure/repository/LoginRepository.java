package com.example.ts.infrastructure.repository;

import com.example.ts.infrastructure.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
    LoginEntity findByUsername(String Username);
}