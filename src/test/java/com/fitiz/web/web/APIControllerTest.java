package com.fitiz.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class APIControllerTest {

    @InjectMocks
    APIController apiController;

    @Test
    void publicTest() {
        assertEquals( "Hello", apiController.publicEndpoint());
    }
    
    @Test
    void privateTest() {
        assertEquals( "Private Hello", apiController.privateEndpoint());
    }

}
