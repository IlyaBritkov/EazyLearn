package com.eazylearn.configuration;

import com.eazylearn.filter.JwtAuthenticationFilter;
import com.eazylearn.filter.JwtAuthorizationFilter;
import com.eazylearn.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.eazylearn.enums.UserRole.ROLE_ADMIN;
import static com.eazylearn.util.Constants.ADMIN_ENDPOINT;
import static com.eazylearn.util.Constants.ANY_FINAL_ENDPOINT;
import static com.eazylearn.util.Constants.LOGIN_ENDPOINT;
import static com.eazylearn.util.Constants.REFRESH_TOKEN_ENDPOINT;
import static com.eazylearn.util.Constants.REGISTRY_ENDPOINT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security configuration class for JWT based Spring Security application.
 **/
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    }

    // todo: add swagger
    // todo: try to send 401 when token is missed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(authenticationManagerBean(), jwtTokenProvider);
        jwtAuthenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT);

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(REGISTRY_ENDPOINT,
                        LOGIN_ENDPOINT,
                        REFRESH_TOKEN_ENDPOINT + ANY_FINAL_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole(ROLE_ADMIN.toString())
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    // todo
    // ? should we use Bean annotation?
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}