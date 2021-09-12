package com.eazylearn.security.jwt;

import com.eazylearn.entity.User;
import com.eazylearn.enums.UserRole;
import com.eazylearn.enums.UserStatus;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

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
                .grantedAuthorities(mapToGrantedAuthorities(List.of(user.getRole())))
                .enabled(UserStatus.ACTIVE.equals(user.getStatus()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> userRoles) {

        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(toList());
    }

}
