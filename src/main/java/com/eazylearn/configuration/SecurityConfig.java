package com.eazylearn.configuration;

import com.eazylearn.security.jwt.JwtConfigurer;
import com.eazylearn.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor(onConstructor_ = @Autowired)

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    private static final String REGISTRY_ENDPOINT = "/api/v1/auth/registry";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String USER_ENDPOINT = "/api/v1/**";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(REGISTRY_ENDPOINT).permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(USER_ENDPOINT).hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}