package com.example.ts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import com.example.ts.security.PasswordConfig;
@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:3000")
public class SecurityConfig {

    //w bazie danych role zapisuje sie ROLE_NAZWA
    //w konfiguracji role zapisuje sie NAZWA
//    @Value("${jwt.token.key}")
//    private String key;
    //wersja 1

    @Value("${jwt.token.key}")
    private String key;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()     //kazdy moze dojsc do logowania
                                        //.requestMatchers("/book").hasRole("USER") //przykład
                                        .requestMatchers("/api/books").permitAll()
                                        .requestMatchers("/auth/register").permitAll()
                                        .requestMatchers("/api/books/getAll").permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    //wersja 2!!!!!!!!!!!!!!!
//    private JWTTokenFilter jwtTokenFilter;
//    @Autowired
//    public SecurityConfig(JWTTokenFilter jwtTokenFilter){
//        this.jwtTokenFilter = jwtTokenFilter;
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(
//                        authorizationManagerRequestMatcherRegistry ->
//                                authorizationManagerRequestMatcherRegistry
////                                        .requestMatchers("/login").permitAll()     //kazdy moze dojsc do logowania
//                                        //.requestMatchers("/book").hasRole("USER") //przykład
//                                        .requestMatchers("/book").permitAll()
//                                        .requestMatchers("/auth/register").permitAll()
//                                        .requestMatchers("/auth/login").permitAll()
//                                        .requestMatchers("/home").permitAll()
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();      // kodowanie hasla
//    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}