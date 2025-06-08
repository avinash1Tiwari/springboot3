package com.avinash.project.uber.uberApp.security;

import com.avinash.project.uber.uberApp.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${jwt.secretKey}")
    private String jwtSecurityKey;

    private SecretKey getSecreteKey()
    {
        return Keys.hmacShaKeyFor(jwtSecurityKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getAccessToken(User user)
    {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles",user.getUser_roles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))                   ///  10 minutes
                .signWith(getSecreteKey())
                .compact();
    }


    public String generateRefreshToken(User user)
    {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L*60*60*24*30*6))
                .signWith(getSecreteKey())
                .compact();
    }


    public Long getUserIdFromToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
