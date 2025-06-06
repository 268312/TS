package com.example.ts.controller;

import com.example.ts.controller.dto.LoginDto;
import com.example.ts.controller.dto.LoginResponseDto;
import com.example.ts.controller.dto.RegisterDto;
import com.example.ts.controller.dto.RegisterResponseDto;
import com.example.ts.infrastructure.entity.LoginEntity;
import com.example.ts.roles.UserRole;
import com.example.ts.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling user login operations.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginService loginService;

    /**
     * Constructor for LoginController
     * @param loginService The service for handling user login operations
     */
    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {
        LoginResponseDto dto = loginService.login(loginDto);
        String username = loginDto.getUsername();
        UserRole role = loginService.getRoleByUsername(username);
        dto.setRole(role.toString());
        Integer id = loginService.getIdByUsername(username);
        dto.setId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")    //to na razie
    public ResponseEntity<RegisterResponseDto> register(@Validated @RequestBody RegisterDto registerDto) throws Exception {
        RegisterResponseDto dto = loginService.register(registerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


}