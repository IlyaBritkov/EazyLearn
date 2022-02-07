package com.eazylearn.controller;

import com.eazylearn.dto.request.user.UserAuthenticationRequestDTO;
import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserAuthenticationResponseDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<UserAuthenticationResponseDTO> login(@RequestBody UserAuthenticationRequestDTO requestDTO)
            throws BadCredentialsException {

        try {
            String email = requestDTO.getEmail();
            String password = requestDTO.getPassword();

            UserAuthenticationResponseDTO authenticationResponse = authenticationService.login(email, password);
            return ResponseEntity.ok(authenticationResponse);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("registry")
    public ResponseEntity<UserResponseDTO> registry(@RequestBody UserRegistryRequestDTO requestDTO)
            throws BadCredentialsException {
        try {
            UserResponseDTO userResponse = authenticationService.registry(requestDTO);
            return ResponseEntity.ok(userResponse);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }

}
