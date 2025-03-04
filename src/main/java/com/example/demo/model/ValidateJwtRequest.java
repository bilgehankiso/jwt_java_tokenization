package com.example.demo.model;

public class ValidateJwtRequest {
   private String tokenUuid;
    public ValidateJwtRequest(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }
    public String getTokenUuid() {
        return tokenUuid;
    }
    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }
}
