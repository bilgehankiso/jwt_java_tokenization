package com.example.demo.controller;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // JWT oluşturma
    @PostMapping("/generate")
    public ResponseEntity<JwtResponse> generateJwt(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtService.generateToken(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    // JWT doğrulama
    @PostMapping("/validate")
    public ResponseEntity<String> validateJwt(@RequestBody JwtRequest jwtRequest) {
        boolean isValid = jwtService.validateToken(jwtRequest.getInputData());
        return ResponseEntity.ok(isValid ? "Valid Token" : "Invalid Token");
    }

    // JWT çözme
    @PostMapping("/decode")
    public ResponseEntity<Object> decodeJwt(@RequestBody JwtRequest jwtRequest) {
        try {
            var claims = jwtService.decodeToken(jwtRequest.getInputData());
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid Token");
        }
    }
}
