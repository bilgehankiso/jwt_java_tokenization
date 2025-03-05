package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "jwt_tokens")
@Getter
@Setter
public class JwtTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    @Column(nullable = false)
    private String tokenType;

    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    private Date expiredDate;

    @Column(nullable = false)
    private String createdBy;

    private String inputData;

    public JwtTokenEntity() {

    }
    public JwtTokenEntity(String uuid, String token, String tokenType, Date createdDate, Date expiredDate, String createdBy, String inputData) {
        this.uuid = uuid;
        this.token = token;
        this.tokenType = tokenType;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.createdBy = createdBy;
        this.inputData = inputData;
    }
}
