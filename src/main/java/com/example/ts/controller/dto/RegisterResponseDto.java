package com.example.ts.controller.dto;
import com.example.ts.roles.UserRole;

public class RegisterResponseDto {
    private Integer userId;
    private String username;
    private UserRole role;

    public RegisterResponseDto(Integer userId, String username, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
