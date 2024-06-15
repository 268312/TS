package com.example.ts.controller;

import com.example.ts.infrastructure.entity.LoginEntity;
import com.example.ts.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Logging in
     * @param login The login entity containing user credentials
     * @return ResponseEntity containing the authentication token if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginEntity login){
        String token = loginService.userLogin(login);
        if(token == null){
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }

}