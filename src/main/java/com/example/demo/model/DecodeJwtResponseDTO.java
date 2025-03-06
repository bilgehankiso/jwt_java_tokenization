package com.example.demo.model;

import java.util.Date;

public class DecodeJwtResponseDTO {
    private String data;
    private Date expiredDate;

    public DecodeJwtResponseDTO(String data, Date expiredDate) {
        this.data = data;
        this.expiredDate = expiredDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}

