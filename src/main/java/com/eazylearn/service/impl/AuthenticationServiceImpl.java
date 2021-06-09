package com.eazylearn.service.impl;

import com.eazylearn.dto.request.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserAuthenticationResponseDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.security.jwt.JwtTokenProvider;
import com.eazylearn.service.AuthenticationService;
import com.eazylearn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.asList;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public UserAuthenticationResponseDTO login(String email, String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userService.findUserEntityByEmail(email);

        log.info("user after login = " + user);

        String token = jwtTokenProvider.createToken(email, asList(user.getRole()));
        return UserAuthenticationResponseDTO.builder()
                .id(user.getId())
                .email(email)
                .token(token)
                .build();
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public UserResponseDTO registry(UserRegistryRequestDTO registryRequest) throws AuthenticationException {
        String password = registryRequest.getPassword();

        return userService.createUser(registryRequest);
    }
}
