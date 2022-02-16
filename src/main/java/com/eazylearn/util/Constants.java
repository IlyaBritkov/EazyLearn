package com.eazylearn.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Constants {
    /**
     * Endpoints
     **/
    // Swagger
    public static final String SWAGGER2_UI_ENDPOINT = "/**";
    public static final String SWAGGER2_DOCS_ENDPOINT = "/v2/api-docs/**";
    // Auth
    public static final String LOGIN_ENDPOINT_PATH = "/api/v1/auth/login";
    public final static String REFRESH_TOKEN_ENDPOINT_PATH = "/api/v1/auth/token/refresh";
    public final static String REFRESH_TOKEN_ENDPOINT = "/token/refresh";
    // Business logic
    public static final String USERS_ENDPOINT_PATH = "/api/v1/users";
    public static final String CARDSETS_ENDPOINT_PATH = "/api/v1/cardSets";

    public final static String ANY_FINAL_ENDPOINT = "/**";

    // JWT
    public final static String BEARER_PREFIX = "Bearer ";
    public final static String ROLE_PREFIX = "ROLE_";
    public final static String USER_ID_CLAIM = "userId";
    public final static String USER_USERNAME_CLAIM = "username";
    public final static String IS_ACCOUNT_NON_EXPIRED_CLAIM = "isAccountNonExpired";
    public final static String IS_ACCOUNT_NON_LOCKED_CLAIM = "isAccountNonLocked";
    public final static String IS_CREDENTIALS_NON_EXPIRED_CLAIM = "isCredentialsNonExpired";
    public final static String IS_ENABLED_CLAIM = "isEnabled";
    public final static String AUTHORITIES_CLAIM = "authorities";
}
