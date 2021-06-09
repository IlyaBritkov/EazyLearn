package com.eazylearn.security.jwt;

import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import com.eazylearn.enums.UserStatus;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .grantedAuthorities(mapToGrantedAuthorities(asList(user.getRole())))
                .enabled(UserStatus.ACTIVE.equals(user.getStatus()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }
}
