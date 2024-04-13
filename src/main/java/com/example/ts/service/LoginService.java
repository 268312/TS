package com.example.ts.service;

import com.example.ts.infrastructure.entity.LoginEntity;
import com.example.ts.infrastructure.repository.LoginRepository;
import com.example.ts.infrastructure.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {
    private UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    //konstruktor z Autowired, dodac za argument userrepository

    @Value("${jwt.token.key}")
    private String key;

    @Autowired
    public LoginService(PasswordEncoder passwordEncoder, LoginRepository loginRepository, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
    }
    public String userLogin(LoginEntity login){
        //tutaj pobrać dane użytkownika z bazy do porównania
        if(passwordEncoder.matches(login.getPassword(), "hash hasła z bazy danych")){
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis+5*60*1000))
                    .claim("id", "id użytkownika z bazy danych")
                    .claim("role", "rola użytkownika z bazy danych")
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
            return token;
        } else {
            return null; //mozna tez wyrzucic wyjatek
        }
    }
}
