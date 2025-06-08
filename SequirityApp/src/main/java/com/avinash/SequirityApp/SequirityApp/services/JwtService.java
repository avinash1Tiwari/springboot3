package com.avinash.SequirityApp.SequirityApp.services;

import com.avinash.SequirityApp.SequirityApp.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import static java.lang.String.valueOf;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;


    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

//    1.generate token
    public String generateAccessToken(User user)
    {
//        1. create token
       return Jwts.builder()
                .subject(user.getId().toString())                       //// subject is used to identify user
                .claim("email",user.getEmail())                      //// we can pass multiple claims, these are passed in key-value pair
                .claim("roles", Set.of("ADMIN","USER"))              //// Setof => to set value of kwy, here we are passing hardcoded value of set as no roles is defined in user-entity
                .issuedAt(new Date())                                   //// new Date() => used to give current time
                .expiration(new Date(System.currentTimeMillis() + 1000*60))       //// expiry time token expire after 1 minute if it is issued
                .signWith(getSecretKey())                           //// we pass secreteKey in params of signWith
                .compact();
    }



    public String generateRefreshToken(User user)
    {
//        1. create token
        return Jwts.builder()
                .subject(user.getId().toString())                       //// subject is used to identify user
                .issuedAt(new Date())                                   //// new Date() => used to give current time
                .expiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30*6))       //// expiry time refreshToken expire after 6 months if it is issued
                .signWith(getSecretKey())                           //// we pass secreteKey in params of signWith
                .compact();
    }

//    2. get user_id from token
    public Long getUserIdFromToken(String token){

        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());

    }

}
