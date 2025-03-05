package com.example.demo.interfaces;


import com.example.demo.model.DecodeJwtResponse;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;

public interface IJwtService {

    JwtResponse generateJwtToken(JwtRequest jwtRequest);

    boolean validateJwtToken(String uuid);

    DecodeJwtResponse decodeJwtToken(String uuid);
}
