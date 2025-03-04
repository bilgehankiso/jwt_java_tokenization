package com.example.demo.model;

import java.util.Date;

public class JwtResponse {
    private String tokenUuid;
    private String token;
    private String tokenType;
    private Date expiresIn;

    public JwtResponse() {
    }

    public JwtResponse(String tokenUuid, String token, String tokenType, Date expiresIn) {
        this.tokenUuid = tokenUuid;
        this.token = token;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }
}
