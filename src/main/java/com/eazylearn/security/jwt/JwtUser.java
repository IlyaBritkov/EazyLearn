package com.eazylearn.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Builder
public class JwtUser implements UserDetails {

    private final UUID id;

    private final String username;

    private final String email;

    private final String password;

    private final boolean isAccountNonExpired;

    private final boolean isAccountNonLocked;

    private final boolean isCredentialsNonExpired;

    private final boolean isEnabled;

    private final Collection<? extends GrantedAuthority> authorities;
}
