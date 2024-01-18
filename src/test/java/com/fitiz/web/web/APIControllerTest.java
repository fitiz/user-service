package com.fitiz.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class APIControllerTest {

    @InjectMocks
    APIController apiController;

    @Test
    void publicTest() {
        assertEquals( ResponseEntity.ok("Hello"), apiController.publicEndpoint());
    }
    
    @Test
    void privateTest() {
        assertEquals( ResponseEntity.ok("Private Hello"), apiController.privateEndpoint());
    }

}
