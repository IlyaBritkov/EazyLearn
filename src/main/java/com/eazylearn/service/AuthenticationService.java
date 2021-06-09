package com.eazylearn.service;

import com.eazylearn.dto.request.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserAuthenticationResponseDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    UserAuthenticationResponseDTO login(String email, String password) throws AuthenticationException;

    UserResponseDTO registry(UserRegistryRequestDTO registryRequest) throws AuthenticationException;

}
