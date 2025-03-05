package com.example.demo.controller;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.ValidateJwtRequest;
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

    @PostMapping("/generate")
    public ResponseEntity<JwtResponse> generateJwt(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtService.generateToken(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateJwt(@RequestBody ValidateJwtRequest validateJwtRequest) {
        boolean isValid = jwtService.validateToken(validateJwtRequest.getTokenUuid());
        return ResponseEntity.ok(isValid ? "Valid Token" : "Invalid Token");
    }

    @PostMapping("/decode")
    public ResponseEntity<Object> decodeJwt(@RequestBody ValidateJwtRequest jwtRequest) {
        try {
            var claims = jwtService.decodeToken(jwtRequest);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid Token");
        }
    }
}
