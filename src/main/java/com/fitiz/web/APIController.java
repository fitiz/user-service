// src/main/java/com/auth0/example/web/APIController.java

package com.fitiz.web;

import com.fitiz.user.Auth0UserService;
import com.fitiz.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);
    @Autowired
    private Auth0UserService auth0UserService;

    @GetMapping(value = "/api/public/testMessage")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Hello");
    }
    @GetMapping(value = "/api/private/testMessage")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("Private Hello");
    }

    @GetMapping(value = "/api/private-scoped/testMessage")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }

    @GetMapping(value = "/api/private/myDetails")
    public ResponseEntity<String> privateMyDetailsEndpoint(Authentication auth, HttpServletRequest request) {

        logger.info("APIController.privateMyDetailsEndpoint");
        String token = (String) request.getAttribute("jwtToken");
        logger.info("token: (RECEIVED)" + token);
        return ResponseEntity.ok(this.auth0UserService.fetchUserDetails(token));
    }
}
