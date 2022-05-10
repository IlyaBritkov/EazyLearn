package com.eazylearn.security.jwt;

import com.eazylearn.entity.User;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("User with email '{}' was successfully loaded", email);

        return jwtUser;
    }

    public static boolean isUserHasAuthority(JwtUser jwtUser, String authority) {
        return jwtUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(name -> name.equals(authority));
    }

}
