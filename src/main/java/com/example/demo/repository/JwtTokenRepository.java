package com.example.demo.repository;

import com.example.demo.model.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    Optional<JwtTokenEntity> findByUuid(String uuid);
}
