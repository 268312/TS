package com.example.ts.service;

import com.example.ts.infrastructure.entity.LoginEntity;
import com.example.ts.roles.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private long tokenLifetime = 3600 *1000;
    @Value("${jwt.token.key}")
    private static String key;
    public String generateToken(LoginEntity userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public static UserRole getRole(String token){
        String roleString = extractClaim(token, (Claims) -> Claims.get("role", String.class));
        return UserRole.valueOf(roleString);
    }

    public static boolean isTokenValid(String token){
        try{
            return !isTokenExpired(token);
        } catch (Exception e){
            return false;
        }
    }
    public static String getUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private String generateToken(Map<String, Object> extraClaims, LoginEntity userDetails){
        extraClaims.put("role", userDetails.getRole());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSigningKey())
                .compact();
    }

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

}
