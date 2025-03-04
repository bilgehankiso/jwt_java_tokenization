package com.example.demo.service;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final String SECRET_KEY = "your_secret_key";

    // JWT oluşturma
    public JwtResponse generateToken(JwtRequest jwtRequest) {
        String tokenUuid = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .setSubject(jwtRequest.getClientName())
                .claim("inputData", jwtRequest.getInputData())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return new JwtResponse(tokenUuid, token, "Bearer", new Date(System.currentTimeMillis() + 1000 * 60 * 60)); // 1 hour expiration
    }

    // JWT doğrulama
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT çözme
    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
//        return null;
    }
}
