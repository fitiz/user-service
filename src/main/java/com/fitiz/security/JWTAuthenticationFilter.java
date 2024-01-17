package com.fitiz.security;

import org.springframework.beans.factory.annotation.Value;

//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.security.interfaces.RSAPublicKey;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    //@Value("${okta.oauth2.issuer}")
    private String AUTH0_DOMAIN =  "https://dev-mewzvus7n0bttt3p.us.auth0.com/";

    //@Value("${okta.oauth2.audience}")
    private String AUDIENCE = "https://api.fitiz.com/api";


    private String AUTH0_USERS_API =  "https://dev-mewzvus7n0bttt3p.us.auth0.com/api/v2/users/";

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
        logger.info("JWTAuthenticationFilter.doFilterInternal");
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            logger.info("!token: " + token);
            if (isTokenValid(token)) {
                // Token is valid
                logger.info("Token is valid");
                response.setStatus(HttpServletResponse.SC_OK);

                List<GrantedAuthority> authorities = getAuthoritiesFromToken(token);
                String username = getUsernameFromToken(token);
                 // Create an Authentication object


                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                // Set the Authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Token is valid");
                // Continue the filter chain
                request.setAttribute("jwtToken", token);
                filterChain.doFilter(request, response);
            } else {
                // Handle invalid token
                logger.info("Token is invalid");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        //filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String token) {
        // Implement token validation logic
        try {
            JwkProvider provider = new JwkProviderBuilder(AUTH0_DOMAIN).build();
            DecodedJWT jwt = JWT.decode(token);
            RSAPublicKey publicKey = (RSAPublicKey) provider.get(jwt.getKeyId()).getPublicKey();

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTH0_DOMAIN)
                    .withAudience(AUDIENCE)
                    .build();
            verifier.verify(token);
            return true; // Token is valid
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Token is invalid or expired
        }
    }


    private List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token); // Use your JWT library's decoding method
        // Extract claims
        String scopes = jwt.getClaim("scope").asString();
    
        // Split the roles and create a list of SimpleGrantedAuthority
        return List.of(scopes.split(" ")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);         
        // Get the subject, which in the case of JWT is typically the username
        return jwt.getSubject();
    }
}

