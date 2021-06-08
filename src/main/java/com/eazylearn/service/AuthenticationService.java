package com.eazylearn.service;

import com.eazylearn.dto.response.AuthenticationResponseDTO;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    AuthenticationResponseDTO login(String email, String password) throws AuthenticationException;
}
