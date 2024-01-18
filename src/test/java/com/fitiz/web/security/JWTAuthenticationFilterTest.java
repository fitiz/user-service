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

    private String JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InJ5aW5faTBnTzZROEozMDhHZUh2YSJ9.eyJpc3MiOiJodHRwczovL2Rldi1tZXd6dnVzN24wYnR0dDNwLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnaXRodWJ8NzMwODA0NjMiLCJhdWQiOlsiaHR0cHM6Ly9hcGkuZml0aXouY29tL2FwaSIsImh0dHBzOi8vZGV2LW1ld3p2dXM3bjBidHR0M3AudXMuYXV0aDAuY29tL3VzZXJpbmZvIl0sImlhdCI6MTcwNTUzNDYxNywiZXhwIjoxNzA1NjIxMDE3LCJhenAiOiJ5d2JZTmZ5R2tiRExKMG5VY0M1bE5ta1J0SE5LSEhXVSIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwifQ.TdzEBvgTs4HUKhUf5ioVhb_tbzmPyW-yjACVRcov3ey8-3vCT9PStB0FaQFCDOfLMLttisuN_nPi1uIOxAyB9TmES0jDPYYh8d_XWuYgZkZW3u2w4Cb2pW3EmXPo4CmHFAT08bBgUGi4rlicrE4guKr0SprIyk_nW-UqQq-sCzdh6Ckqf9XUIxQLEcZSeYM98_MJvlnpFUZVQ_2WJ5TUcDZq10FORXGq2V-Oyvr3Xb-_oQ_SqYV4LAo9rLddHcsZ0yHebtxuXq5r-9tv0EFo8DyxRPf3UmRrxNXfB-HjIDqGPHmv3xxD2rV2GAQT17RLrWH5R_3B7J9nTaW2o-bOFQ";

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
            assertEquals("github|73080463", result);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown");

        }
    }


}
