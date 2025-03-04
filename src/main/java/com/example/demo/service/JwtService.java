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

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public JwtResponse generateToken(JwtRequest jwtRequest) {
        String uuid = UUID.randomUUID().toString();

        String token = Jwts.builder()
                .setSubject(jwtRequest.getClientName())
                .claim("inputData", jwtRequest.getInputData())
                .signWith(SECRET_KEY)
                .compact();

        JwtResponse jwtResponse = new JwtResponse(uuid, token, "Bearer", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));

        return jwtResponse;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  // Validate with the same SECRET_KEY
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Decode with the same SECRET_KEY
                .parseClaimsJws(token)
                .getBody();
    }
}
