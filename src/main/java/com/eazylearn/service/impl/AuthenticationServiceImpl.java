package com.eazylearn.service.impl;

import com.eazylearn.dto.response.AuthenticationResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.security.jwt.JwtTokenProvider;
import com.eazylearn.service.AuthenticationService;
import com.eazylearn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public AuthenticationResponseDTO login(String email, String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", email)));


        String token = jwtTokenProvider.createToken(email, asList(user.getRole()));
        return AuthenticationResponseDTO.builder()
                .email(email)
                .token(token)
                .build();
    }
}
