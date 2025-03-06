package com.example.demo.interfaces;


import com.example.demo.model.DecodeJwtResponseDTO;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;

public interface IJwtService {

    JwtResponse generateJwtToken(JwtRequest jwtRequest);

    boolean validateJwtToken(String uuid);

    DecodeJwtResponseDTO decodeJwtToken(String uuid);
}
