package com.example.ts.controller.dto;

import com.example.ts.roles.UserRole;

public class LoginResponseDto {
    private String token;
    private String role;
    private Integer id;
//    private String role;

    public LoginResponseDto(String token, String role){
        this.token = token;
        this.role = this.role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}
