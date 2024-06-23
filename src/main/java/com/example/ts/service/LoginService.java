package com.example.ts.service;

import com.example.ts.controller.dto.LoginDto;
import com.example.ts.controller.dto.LoginResponseDto;
import com.example.ts.controller.dto.RegisterDto;
import com.example.ts.controller.dto.RegisterResponseDto;
import com.example.ts.infrastructure.entity.LoginEntity;
import com.example.ts.infrastructure.entity.UserEntity;
import com.example.ts.infrastructure.repository.LoginRepository;
import com.example.ts.infrastructure.repository.UserRepository;
import com.example.ts.roles.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoginService {
    private UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    //konstruktor z Autowired, dodac za argument userrepository
    private JWTService jwtService;

//    @Value("${jwt.token.key}")
//    private String key;

    @Autowired
    public LoginService(PasswordEncoder passwordEncoder, LoginRepository loginRepository, UserRepository userRepository, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
//    public String userLogin(LoginEntity login){
//        //tutaj pobrać dane użytkownika z bazy do porównania
//        if(passwordEncoder.matches(login.getPassword(), "hash hasła z bazy danych")){
//            long timeMillis = System.currentTimeMillis();
//            String token = Jwts.builder()
//                    .issuedAt(new Date(timeMillis))
//                    .expiration(new Date(timeMillis+5*60*1000))
//                    .claim("id", "id użytkownika z bazy danych")
//                    .claim("role", "rola użytkownika z bazy danych")
//                    .signWith(SignatureAlgorithm.HS256, key)
//                    .compact();
//            return token;
//        } else {
//            return null; //mozna tez wyrzucic wyjatek
//        }
//    }
//    public LoginEntity registerNewUser(LoginEntity user) throws Exception {
//        if (loginRepository.findByUsername(user.getUsername()) != null) {
//            throw new Exception("Username is already taken!");
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return loginRepository.save(user);
//    }
//}

    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto) throws Exception{
        LoginEntity existingLogin = loginRepository.findByUsername(registerDto.getUsername());
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setName(registerDto.getUsername());
        userEntity.setFullName(registerDto.getFullName());
        userRepository.save(userEntity);
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        loginEntity.setUsername(registerDto.getUsername());
        loginEntity.setRole(registerDto.getRole());
        loginEntity.setUser(userEntity);
        loginRepository.save(loginEntity);
        return new RegisterResponseDto(userEntity.getId(), loginEntity.getUsername(), loginEntity.getRole());
    }

    public LoginResponseDto login(LoginDto loginDto) throws Exception{
       LoginEntity loginEntity = loginRepository.findByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), loginEntity.getPassword())) {
            throw new Exception("The password is incorrect");
        }
        String token = jwtService.generateToken(loginEntity);
        String role = loginEntity.getRole().toString();
        return new LoginResponseDto(token, role);
//        return new LoginResponseDto(token);
        }
    public UserRole getRoleByUsername(String username){
        LoginEntity loginEntity = loginRepository.findByUsername(username);
        UserRole role = loginEntity.getRole();
        return role;
    }
    }
