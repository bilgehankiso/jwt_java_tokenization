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
        try {
            JwtResponse jwtResponse = jwtService.generateJwtToken(jwtRequest);
            if (jwtResponse == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateJwt(@RequestBody ValidateJwtRequest validateJwtRequest) {
        try {
            boolean isValid = jwtService.validateJwtToken(validateJwtRequest.getTokenUuid());
            return ResponseEntity.ok(isValid ? "JWT token is valid" : "JWT token is invalid");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/decode")
    public ResponseEntity<DecodeJwtResponseDTO> decodeJwt(@RequestBody ValidateJwtRequest jwtRequest) {
        try {
            var result = jwtService.decodeJwtToken(jwtRequest.getTokenUuid());
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
