package com.example.demo.service;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String SECRET_KEY = "your-secret-key";

    public JwtResponse generateToken(JwtRequest jwtRequest) {

        String uuid = UUID.randomUUID().toString();

        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .setSubject(uuid)
                .claim("inputData", jwtRequest.getInputData())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        JwtResponse jwtResponse = new JwtResponse(uuid, token, "Bearer", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));

        return jwtResponse;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}

