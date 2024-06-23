package com.example.ts.controller.dto;

public class DeleteUserDto {
    private Integer id;

    public DeleteUserDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
