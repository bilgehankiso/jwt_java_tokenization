package com.example.demo.interfaces;


import com.example.demo.model.DecodeJwtResponse;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;

public interface IJwtService {

    JwtResponse generateToken(JwtRequest jwtRequest);

    boolean validateToken(String uuid);

    DecodeJwtResponse decodeToken(String uuid);
}
