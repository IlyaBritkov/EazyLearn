package com.eazylearn.controller;

import com.eazylearn.dto.request.AuthenticationRequestDTO;
import com.eazylearn.dto.response.AuthenticationResponseDTO;
import com.eazylearn.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")    // todo add global exception handling
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            String email = requestDTO.getEmail();
            String password = requestDTO.getPassword();

            AuthenticationResponseDTO authenticationResponse = authenticationService.login(email, password);
            return ResponseEntity.ok(authenticationResponse);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
