package com.example.demo.service;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String SECRET_KEY = "M2Y1YTk4YzAxZjY0NDYzZWZmY2QyYTkzZDI5ZDU2ZDZkZjY4YzA0ZGViZDNlMGNhZTBiZDE2OTkxNzNi";

    public JwtResponse generateToken(JwtRequest jwtRequest) {
        String uuid = UUID.randomUUID().toString();

        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");

        String token = Jwts.builder()
                .setSubject(jwtRequest.getClientName())
                .claim("inputData", jwtRequest.getInputData())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new JwtResponse(uuid, token, "Bearer", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
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
