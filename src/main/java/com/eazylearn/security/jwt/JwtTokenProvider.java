package com.eazylearn.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.eazylearn.util.Constants.AUTHORITIES_CLAIM;
import static com.eazylearn.util.Constants.IS_ACCOUNT_NON_EXPIRED_CLAIM;
import static com.eazylearn.util.Constants.IS_ACCOUNT_NON_LOCKED_CLAIM;
import static com.eazylearn.util.Constants.IS_CREDENTIALS_NON_EXPIRED_CLAIM;
import static com.eazylearn.util.Constants.IS_ENABLED_CLAIM;
import static com.eazylearn.util.Constants.USER_USERNAME_CLAIM;
import static com.eazylearn.util.Constants.USER_ID_CLAIM;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String JWT_SECRET;
    @Value("${jwt.token.access.expiration}")
    private long ACCESS_TOKEN_EXPIRATION_IN_MILLISECONDS;
    @Value("${jwt.token.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRATION_IN_MILLISECONDS;

    private Algorithm algorithm;

    @PostConstruct
    private void postConstruct() {
        algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
    }

    public String generateAccessToken(JwtUser jwtUser, HttpServletRequest request) {
        final List<String> authorities = jwtUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());

        return JWT.create()
                .withSubject(jwtUser.getEmail())
                .withExpiresAt(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_IN_MILLISECONDS))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(USER_ID_CLAIM, jwtUser.getId())
                .withClaim(USER_USERNAME_CLAIM, jwtUser.getEmail())
                .withClaim(IS_ACCOUNT_NON_EXPIRED_CLAIM, jwtUser.isAccountNonExpired())
                .withClaim(IS_ACCOUNT_NON_LOCKED_CLAIM, jwtUser.isAccountNonLocked())
                .withClaim(IS_CREDENTIALS_NON_EXPIRED_CLAIM, jwtUser.isCredentialsNonExpired())
                .withClaim(IS_ENABLED_CLAIM, jwtUser.isEnabled())
                .withClaim(AUTHORITIES_CLAIM, authorities)
                .sign(algorithm);
    }

    public String generateRefreshToken(JwtUser jwtUser, HttpServletRequest request) {
        return JWT.create()
                .withSubject(jwtUser.getEmail())
                .withExpiresAt(new Date(currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_IN_MILLISECONDS))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public JwtUser getJwtUserFromToken(String token) {
        final DecodedJWT decodedJWT = decode(token);

        final String email = decodedJWT.getSubject();
        final Map<String, Claim> claims = decodedJWT.getClaims();
        final String userId = claims.get(USER_ID_CLAIM).asString();
        final String username = claims.get(USER_USERNAME_CLAIM).asString();
        final boolean isAccountNonExpired = claims.get(IS_ACCOUNT_NON_EXPIRED_CLAIM).asBoolean();
        final boolean isAccountNonLocked = claims.get(IS_ACCOUNT_NON_LOCKED_CLAIM).asBoolean();
        final boolean isCredentialsNonExpired = claims.get(IS_CREDENTIALS_NON_EXPIRED_CLAIM).asBoolean();
        final boolean isEnabled = claims.get(IS_ENABLED_CLAIM).asBoolean();
        final List<SimpleGrantedAuthority> authorities = mapStringAuthoritiesToSimpleGrantedAuthorities(claims.get(AUTHORITIES_CLAIM).asList(String.class));

        return JwtUserFactory.jwtUserBuilder()
                .id(userId)
                .username(username)
                .email(email)
                .isAccountNonExpired(isAccountNonExpired)
                .isAccountNonLocked(isAccountNonLocked)
                .isCredentialsNonExpired(isCredentialsNonExpired)
                .isEnabled(isEnabled)
                .authorities(authorities)
                .build();
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

    public static List<SimpleGrantedAuthority> mapStringAuthoritiesToSimpleGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
