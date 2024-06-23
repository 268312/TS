package com.example.ts.controller.dto;

import com.example.ts.roles.UserRole;

public class RegisterDto {
    private String password;
    private String username;
    private UserRole role;
    private String email;
    private String fullName;

    public RegisterDto(String password, String username, UserRole role, String email, String fullName){
        this.password = password;
        this.username = username;
        this.role = role;
        this.email = email;
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
