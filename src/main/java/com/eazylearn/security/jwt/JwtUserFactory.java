package com.eazylearn.security.jwt;

import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;

import static com.eazylearn.enums.UserStatus.ACTIVE;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(mapUserRolesAndAuthoritiesToAuthorities(user.getRoles()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(Objects.equals(ACTIVE, user.getStatus()))
                .build();
    }

    private static List<GrantedAuthority> mapUserRolesAndAuthoritiesToAuthorities(List<Role> userRoles) {
        final List<GrantedAuthority> authorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(toList());

        userRoles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getName().name())));

        return authorities;
    }

}
