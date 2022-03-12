package com.eazylearn.configuration;

import com.eazylearn.filter.JwtAuthenticationFilter;
import com.eazylearn.filter.JwtAuthorizationFilter;
import com.eazylearn.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.eazylearn.util.Constants.ANY_FINAL_ENDPOINT;
import static com.eazylearn.util.Constants.LOGIN_ENDPOINT_PATH;
import static com.eazylearn.util.Constants.REFRESH_TOKEN_ENDPOINT_PATH;
import static com.eazylearn.util.Constants.SWAGGER2_API_DOCS_ENDPOINT;
import static com.eazylearn.util.Constants.SWAGGER2_RESOURCES_ENDPOINT;
import static com.eazylearn.util.Constants.SWAGGER2_UI_ENDPOINT;
import static com.eazylearn.util.Constants.SWAGGER2_UI_HTML_ENDPOINT;
import static com.eazylearn.util.Constants.USERS_ENDPOINT_PATH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security configuration class for JWT based Spring Security application.
 **/
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bcryptPasswordEncoder);
    }

    // todo: try to send 401 when token is missed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(authenticationManagerBean(), jwtTokenProvider);

        jwtAuthenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT_PATH);

        http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                // todo: permit all to refresh endpoint?
                .antMatchers(LOGIN_ENDPOINT_PATH,
                        REFRESH_TOKEN_ENDPOINT_PATH + ANY_FINAL_ENDPOINT,
                        SWAGGER2_API_DOCS_ENDPOINT + ANY_FINAL_ENDPOINT,
                        SWAGGER2_UI_ENDPOINT + ANY_FINAL_ENDPOINT,
                        SWAGGER2_RESOURCES_ENDPOINT + ANY_FINAL_ENDPOINT,
                        SWAGGER2_UI_HTML_ENDPOINT).permitAll()
                .antMatchers(POST, USERS_ENDPOINT_PATH + ANY_FINAL_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    // todo
    // ? should we use Bean annotation?
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping(LOGIN_ENDPOINT_PATH)
                        .allowedOriginPatterns("*");
            }
        };
    }
}