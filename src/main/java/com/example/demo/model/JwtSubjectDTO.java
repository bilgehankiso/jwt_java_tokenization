package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class JwtSubjectDTO {
    private String created_by;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date expired_date;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created_date;
    private String uuid;
    private String data;

    public JwtSubjectDTO() {
    }

    public JwtSubjectDTO(String created_by, Date expired_date, Date created_date, String uuid, String data) {
        this.created_by = created_by;
        this.expired_date = expired_date;
        this.created_date = created_date;
        this.uuid = uuid;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(Date expired_date) {
        this.expired_date = expired_date;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}