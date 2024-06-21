package com.example.ts.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.ts.service.JWTService;

import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
public class JWTTokenFilter extends OncePerRequestFilter {
//    private JWTService jwtService;
//    public JWTTokenFilter(JWTService jwtService){
//        this.jwtService = jwtService;
//    }
    private final String key;
    public JWTTokenFilter(String key){
        this.key = key;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuth!=null && headerAuth.startsWith("Bearer ")){
            String token = headerAuth.substring(7);   //odcina bearer
            try {
                if (JWTService.isTokenValid(token)) {
                    String username = JWTService.getUsername(token);
                    String role = JWTService.getRole(token).name();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(role)));
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                } catch (Exception e) {
                logger.error("User authentication error", e);
            }
            }

//            Claims claims = Jwts.parser()
//                    .setSigningKey(key)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//            String userId = (String) claims.get("id"); //to będzie zapisane w tokenie przy
//            String role = (String) claims.get("role"); // tworzeniu
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(role)));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }

        filterChain.doFilter(request, response);
    }
}
