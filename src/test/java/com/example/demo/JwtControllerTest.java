package com.example.demo;

import com.example.demo.controller.JwtController;
import com.example.demo.model.DecodeJwtResponse;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.ValidateJwtRequest;
import com.example.demo.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtControllerTest {

    @Autowired
    private JwtController jwtController;


    @Test
    void contextLoads() {
        assertNotNull(jwtController);
    }

    @Test
    void testGenerateJwt() {
        JwtRequest jwtRequest = new JwtRequest("clientName", "inputData");

        ResponseEntity<JwtResponse> response = jwtController.generateJwt(jwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
        assertNotNull(response.getBody().getTokenUuid());
        assertNotNull(response.getBody().getTokenType());
        assertFalse(response.getBody().getToken().isEmpty());
    }

    @Test
    void testValidateJwt_Valid() {
        ValidateJwtRequest validateJwtRequest = new ValidateJwtRequest("8b36e674-e58e-430f-8670-70ad33ccd1c9");

        ResponseEntity<String> response = jwtController.validateJwt(validateJwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("JWT token is valid", response.getBody());
    }
    @Test
    void testValidateJwt_Invalid() {
        ValidateJwtRequest validateJwtRequest = new ValidateJwtRequest("8b36e674-e58e-430f-8670-70ad33ccd1c9999");

        ResponseEntity<String> response = jwtController.validateJwt(validateJwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("JWT token is invalid", response.getBody());
    }

    @Test
    void testDecodeJwt_Decoced() {
        ValidateJwtRequest validateJwtRequest = new ValidateJwtRequest("8b36e674-e58e-430f-8670-70ad33ccd1c9");

        ResponseEntity<DecodeJwtResponse> response = jwtController.decodeJwt(validateJwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertNotNull(response.getBody().getExpiredDate());
    }
    @Test
    void testDecodeJwt_NotDecoced() {
        ValidateJwtRequest validateJwtRequest = new ValidateJwtRequest("8b36e674-e58e-430f-8670-70ad33ccd1c9999");

        ResponseEntity<DecodeJwtResponse> response = jwtController.decodeJwt(validateJwtRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
