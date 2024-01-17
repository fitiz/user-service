package com.fitiz.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.*;
import static org.junit.jupiter.api.Assertions.fail;
import java.lang.Class;


@ExtendWith(MockitoExtension.class)
class JWTAuthencticationFilterTest {

    private String JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InJ5aW5faTBnTzZROEozMDhHZUh2YSJ9.eyJpc3MiOiJodHRwczovL2Rldi1tZXd6dnVzN24wYnR0dDNwLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnaXRodWJ8NzMwODA0NjMiLCJhdWQiOlsiaHR0cHM6Ly9hcGkuZml0aXouY29tL2FwaSIsImh0dHBzOi8vZGV2LW1ld3p2dXM3bjBidHR0M3AudXMuYXV0aDAuY29tL3VzZXJpbmZvIl0sImlhdCI6MTcwNTQ0ODExOCwiZXhwIjoxNzA1NTM0NTE4LCJhenAiOiJ5d2JZTmZ5R2tiRExKMG5VY0M1bE5ta1J0SE5LSEhXVSIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwifQ.FqUlgq95KC-LmNGr1uU7A1kVbDNsI6NStZfeM4_-PAAXutZKKAQzeqscktTNYQZyEFpKauc4ysATDhr1d_NY8zRjb8rkhBK-_lCovP4NOtX1y2NmUa9HG-kquC4OuSEo2LtAB_D86UUBPQWE1iEVjCBi367sNp2dns7ejP2mZZecYwx3Ml6Xt8I_GAn8vyoD1554FpVJfXsYyc5kzKQJsbWgZbiJWEp3AGSCbu-gi4k4bdYHKlggBgh-ueqPa6tiq5uvikEl71LjpcG_OYEth0pJsyFPhBYzyWs4MTxgNITfxIPL85J2_V0mjAfwVQKK63BqQJ2TEaSH7UCZFjpPNw";

    @InjectMocks
    JWTAuthenticationFilter jwtFilter;

    @Test
    void validateTokenTest() {
        try {
            Method method = JWTAuthenticationFilter.class.getDeclaredMethod("isTokenValid", String.class);
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(jwtFilter, JWT);
            assertEquals(true, result); 

        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");
        }
    }
    
    @Test
    void extractUserTest() {
        try {
            Method method = JWTAuthenticationFilter.class.getDeclaredMethod("getUsernameFromToken", String.class);
            method.setAccessible(true);
 
            String result = (String) method.invoke(jwtFilter, JWT);
            assertEquals( "github|73080463", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");

        }
    }


}
