package com.eazylearn.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Returns JWT UserDetails for current authenticated user
     **/
    public JwtUser getJwtPrincipal() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Returns ID of current authenticated user
     **/
    public String getJwtPrincipalId() {
        return ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
