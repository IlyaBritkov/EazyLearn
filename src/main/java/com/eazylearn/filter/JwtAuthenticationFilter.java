package com.eazylearn.filter;

import com.eazylearn.security.jwt.JwtTokenProvider;
import com.eazylearn.security.jwt.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        log.info("Email is: {}", email);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();

        String accessToken = jwtTokenProvider.generateAccessToken(jwtUser, request);
        String refreshToken = jwtTokenProvider.generateRefreshToken(jwtUser, request);

        Map<String, String> tokensMap = new HashMap<>();
        tokensMap.put("access_token", accessToken);
        tokensMap.put("refresh_token", refreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        // todo: make ObjectMapper a bean
        new ObjectMapper().writeValue(response.getOutputStream(), tokensMap);
    }
}
