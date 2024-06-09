package com.example.ts.service;

import com.example.ts.infrastructure.entity.UserEntity;
import com.example.ts.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity get(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public UserEntity addUser(UserEntity user){
        return userRepository.save(user);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }
    public boolean existsByUsername(String name) {
        return userRepository.existsByName(name);
    }
}
