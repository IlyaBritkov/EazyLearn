package com.eazylearn.security.jwt;

import com.eazylearn.entity.Role;
import com.eazylearn.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityInSeconds;

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email, List<Role> userRoles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", getRolesNames(userRoles));

        LocalDateTime now = now();
        LocalDateTime validity = now.plusSeconds(validityInSeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(mapToDateViaSqlTimestamp(now))
                .setExpiration(mapToDateViaSqlTimestamp(validity))
                .signWith(HS256, secret)
                .compact()
                ;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration()
                    .before(mapToDateViaSqlTimestamp(now()));
        } catch (JwtException | IllegalArgumentException ex) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRolesNames(List<Role> userRoles) {
        return userRoles.stream()
                .map(Role::getName)
                .collect(toList());
    }

    public Date mapToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return valueOf(dateToConvert);
    }
}
