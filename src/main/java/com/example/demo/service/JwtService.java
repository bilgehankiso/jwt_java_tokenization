package com.example.demo.service;

import com.example.demo.entity.JwtTokenEntity;
import com.example.demo.interfaces.IJwtService;
import com.example.demo.model.*;
import com.example.demo.repository.JwtTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class JwtService implements IJwtService {

    private final JwtTokenRepository IJwtTokenRepository;

    private static final String SECRET_KEY = "M2Y1YTk4YzAxZjY0NDYzZWZmY2QyYTkzZDI5ZDU2ZDZkZjY4YzA0ZGViZDNlMGNhZTBiZDE2OTkxNzNi";

    public JwtService(JwtTokenRepository IJwtTokenRepository) {
        this.IJwtTokenRepository = IJwtTokenRepository;
    }

    public JwtResponse generateToken(JwtRequest jwtRequest) {
        String uuid = UUID.randomUUID().toString();

        Date createdDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");

        Map<String, String> subject = new HashMap<>();
        subject.put("created_by", jwtRequest.getClientName());
        subject.put("created_date", formatToISO(createdDate));
        subject.put("expired_date", formatToISO(expiredDate));

        String subjectJson;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            subjectJson = objectMapper.writeValueAsString(subject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        String token = Jwts.builder()
                .setSubject(subjectJson)
                .claim("inputData", jwtRequest.getInputData())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        JwtTokenEntity jwtTokenEntity = new JwtTokenEntity();
        jwtTokenEntity.setUuid(uuid);
        jwtTokenEntity.setToken(token);
        jwtTokenEntity.setTokenType("Bearer");
        jwtTokenEntity.setCreatedDate(createdDate);
        jwtTokenEntity.setExpiredDate(expiredDate);
        jwtTokenEntity.setCreatedBy(jwtRequest.getClientName());
        jwtTokenEntity.setInputData(jwtRequest.getInputData());

        IJwtTokenRepository.save(jwtTokenEntity);


        return new JwtResponse(uuid, token, "Bearer", new Date(System.currentTimeMillis() + 1000 * 60 * 60));
    }

    public boolean validateToken(String uuid) {
        Optional<JwtTokenEntity> tokenEntity = IJwtTokenRepository.findByUuid(uuid);
        if (tokenEntity.isPresent()) {
            String token = tokenEntity.get().getToken();
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                JwtSubject tokenInfo = null;
                try {
                    tokenInfo = objectMapper.readValue(claims.getSubject(), JwtSubject.class);
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (tokenInfo != null) {
                    return true;
                }

            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public DecodeJwtResponse decodeToken(String uuid) {
        Optional<JwtTokenEntity> tokenEntity = IJwtTokenRepository.findByUuid(uuid);
        if (tokenEntity.isPresent()) {
            String token = tokenEntity.get().getToken();

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JwtSubject tokenInfo = null;
            try {
                tokenInfo = objectMapper.readValue(claims.getSubject(), JwtSubject.class);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (tokenInfo != null) {
                DecodeJwtResponse decodeJwtResponse = new DecodeJwtResponse(tokenEntity.get().getInputData(), tokenInfo.getExpired_date());
                return decodeJwtResponse;
            }
        }
        return null;
    }

    private static String formatToISO(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(java.time.ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return zonedDateTime.format(formatter);
    }
}
