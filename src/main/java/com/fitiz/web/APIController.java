// src/main/java/com/auth0/example/web/APIController.java

package com.fitiz.web;


import com.fitiz.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
//@CrossOrigin(origins = "localhost")
public class APIController {


    @GetMapping(value = "/public/testMessage")
    public String publicEndpoint() {
        return "Hello";//new Message("All good. You DO NOT need to be authenticated to call /api/public endpoints.");
    }
    @GetMapping(value = "/private/testMessage")
    public String privateEndpoint() {
        return "Private Hello";//new Message("All good. You can see this because you are Authenticated.");
    }

    @GetMapping(value = "/private-scoped/testMessage")
    public Message privateScopedEndpoint() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
