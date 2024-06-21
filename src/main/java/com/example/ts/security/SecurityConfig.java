package com.example.ts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:3000")
public class SecurityConfig {

    private final JWTTokenFilter jwtTokenFilter;
    @Autowired
    public SecurityConfig(JWTTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
//                                        .requestMatchers("/login").permitAll()     //kazdy moze dojsc do logowania
                                        //.requestMatchers("/book").hasRole("USER") //przykład
                                        //books
                                        .requestMatchers("/api/books/getAll").permitAll()
                                        .requestMatchers("/api/books/add").hasRole("ADMIN")
                                        .requestMatchers("/api/books/delete").permitAll()
                                        //auth
                                        .requestMatchers("/auth/register").permitAll()
                                        .requestMatchers("/auth/login").permitAll()
                                        //
                                        .requestMatchers("/home").hasRole("ADMIN")
                                        //loans
                                        .requestMatchers("/api/loan/all").hasRole("ADMIN")
                                        .requestMatchers("/api/loan/borrow").hasRole("ADMIN")
                                        //user
                                        .requestMatchers("/api/user/getAll").hasRole("ADMIN")

                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();      // kodowanie hasla
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}