package com.eazylearn.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.eazylearn.security.jwt.JwtTokenProvider;
import com.eazylearn.security.jwt.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.eazylearn.util.Constants.AUTH_ENDPOINT_PATH;
import static com.eazylearn.util.Constants.BEARER_PREFIX;
import static com.eazylearn.util.Constants.REFRESH_TOKEN_ENDPOINT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AUTH_ENDPOINT_PATH)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AuthenticationRestController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    // todo: refresh token is disposable. Should be stored in the database
    @GetMapping(REFRESH_TOKEN_ENDPOINT)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            try {
                final String oldRefreshToken = authorizationHeader.substring(BEARER_PREFIX.length());

                final DecodedJWT decodedToken = jwtTokenProvider.verifyToken(oldRefreshToken);

                String email = decodedToken.getSubject();
                JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(email);

                final String accessToken = jwtTokenProvider.generateAccessToken(jwtUser, request);
                final String newRefreshToken = jwtTokenProvider.generateRefreshToken(jwtUser, request);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", newRefreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception ex) {
                String exMessage = ex.getMessage();
                log.error("Error in token resolving: {}", exMessage);

                response.setHeader("error", exMessage);
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exMessage);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
