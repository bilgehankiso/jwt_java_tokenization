package com.example.demo.controller;

import com.example.demo.interfaces.IJwtService;
import com.example.demo.model.DecodeJwtResponseDTO;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.ValidateJwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final IJwtService jwtService;

    public JwtController(IJwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/generate")
    public ResponseEntity<JwtResponse> generateJwt(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtService.generateJwtToken(jwtRequest);
        if (jwtResponse != null) {
            return ResponseEntity.ok(jwtResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateJwt(@RequestBody ValidateJwtRequest validateJwtRequest) {
        boolean isValid = jwtService.validateJwtToken(validateJwtRequest.getTokenUuid());
        if (isValid) {
            return ResponseEntity.ok("JWT token is valid");
        } else {
            return ResponseEntity.ok("JWT token is not valid");
        }
    }

    @PostMapping("/decode")
    public ResponseEntity<DecodeJwtResponseDTO> decodeJwt(@RequestBody ValidateJwtRequest jwtRequest) {
        DecodeJwtResponseDTO result = jwtService.decodeJwtToken(jwtRequest.getTokenUuid());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
