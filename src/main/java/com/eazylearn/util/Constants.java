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

    public final static String ANY_FINAL_ENDPOINT = "/**";

    // JWT
    public final static String BEARER_PREFIX = "Bearer "; // todo: maybe change to "Bearer_"
    public final static String AUTHORITIES_CLAIM = "authorities";
    public final static String USER_ID_CLAIM = "user_id";
}
