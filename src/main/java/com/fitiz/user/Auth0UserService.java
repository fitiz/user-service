package com.fitiz.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class Auth0UserService {

    private static final Logger logger = LoggerFactory.getLogger(Auth0UserService.class); 
    private final RestTemplate restTemplate;

    public Auth0UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //syncronous -> not very scalable
    public String fetchUserDetails(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String userId = jwt.getSubject().split("|")[1];
        String encodedUserId = encodeValue(userId);
        logger.info("userId: " + userId);

        String url = UriComponentsBuilder
                .fromHttpUrl("https://dev-mewzvus7n0bttt3p.us.auth0.com/userinfo")
                .toUriString();


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }


    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            logger.error("Error encoding value: " + value);
            throw new RuntimeException(e.getCause());
        }
    }
}
