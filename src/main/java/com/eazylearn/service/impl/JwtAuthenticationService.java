package com.eazylearn.service.impl;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserAuthenticationResponseDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.security.jwt.JwtTokenProvider;
import com.eazylearn.service.AuthenticationService;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationService implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserAuthenticationResponseDTO login(String email, String password) throws AuthenticationException {
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email = %s doesn't exist", email)));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); // TODO: !FIX -> cause runtime exception

        log.info("Authorized user = {}", user);

        String token = jwtTokenProvider.createToken(email, user.getRoles());

        return UserAuthenticationResponseDTO.builder()
                .id(user.getId())
                .email(email)
                .token(token)
                .build();
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public UserResponseDTO registry(UserRegistryRequestDTO registryRequest) throws AuthenticationException {
        final User createdUser = userService.createUser(registryRequest);

        return userMapper.toResponseDTO(createdUser);
    }

}
