package eazy.learn.security;

import eazy.learn.entity.User;
import eazy.learn.security.jwt.JwtUser;
import eazy.learn.security.jwt.JwtUserFactory;
import eazy.learn.service.UserService;
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
        User user = userService.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("User with email '{}' successfully loaded", email);
        return jwtUser;
    }
}
