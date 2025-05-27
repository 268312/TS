package com.example.ts;

import com.example.ts.controller.dto.RegisterDto;
import com.example.ts.controller.dto.RegisterResponseDto;
import com.example.ts.roles.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsApplication.class, args);
    }

}
