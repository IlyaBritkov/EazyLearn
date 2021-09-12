package com.eazylearn.security;

import com.eazylearn.entity.User;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.security.jwt.JwtUserFactory;
import com.eazylearn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserEntityByEmail(email);

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("User with email '{}' successfully loaded", email);

        return jwtUser;
    }

}
