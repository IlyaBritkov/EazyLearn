package com.eazylearn.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.eazylearn.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eazylearn.security.jwt.JwtTokenProvider.mapStringAuthoritiesToSimpleGrantedAuthorities;
import static com.eazylearn.util.Constants.AUTHORITIES_CLAIM;
import static com.eazylearn.util.Constants.BEARER_PREFIX;
import static com.eazylearn.util.Constants.LOGIN_ENDPOINT;
import static com.eazylearn.util.Constants.REFRESH_TOKEN_ENDPOINT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals(LOGIN_ENDPOINT) && !request.getServletPath().equals(REFRESH_TOKEN_ENDPOINT)) {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
                try {
                    String token = authorizationHeader.substring(BEARER_PREFIX.length());
                    DecodedJWT decodedToken = jwtTokenProvider.verifyToken(token);

                    String username = decodedToken.getSubject();
                    List<String> authorities = decodedToken.getClaim(AUTHORITIES_CLAIM).asList(String.class);
                    List<SimpleGrantedAuthority> grantedAuthorities = mapStringAuthoritiesToSimpleGrantedAuthorities(authorities);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception ex) {
                    String exMessage = ex.getMessage();
                    log.error("Error during token resolving: {}", exMessage);

                    response.setHeader("Error", exMessage);
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> responseBody = new HashMap<>();
                    responseBody.put("error_message", exMessage);

                    response.setContentType(APPLICATION_JSON_VALUE);
                    // todo: make ObjectMapper a bean
                    new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
