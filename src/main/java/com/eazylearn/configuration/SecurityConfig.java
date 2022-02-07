package com.eazylearn.configuration;

import com.eazylearn.security.jwt.JwtConfigurer;
import com.eazylearn.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.eazylearn.enums.UserRole.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security configuration class for JWT based Spring Security application.
 **/
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SWAGGER2_UI_ENDPOINT = "/**";
    private static final String SWAGGER2_DOCS_ENDPOINT = "/v2/api-docs/**";
    private static final String REGISTRY_ENDPOINT = "/api/v1/auth/registry";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";

    private final JwtTokenProvider jwtTokenProvider;

    // todo: add swagger
    // todo: FIX
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers(REGISTRY_ENDPOINT, LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole(ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    // todo: figure out if is it required
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}