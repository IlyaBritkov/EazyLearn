package eazy.learn.security.jwt;

import eazy.learn.entity.Role;
import eazy.learn.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static eazy.learn.enums.UserStatus.ACTIVE;
import static java.util.Collections.singletonList;
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
                .grantedAuthorities(mapToGrantedAuthorities(singletonList(user.getRole())))
                .enabled(ACTIVE.equals(user.getStatus()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }
}
