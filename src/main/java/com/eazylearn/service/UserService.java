package com.eazylearn.service;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;
import com.eazylearn.exception_handling.exception.UserAlreadyExistAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findUserById(UUID id) throws EntityDoesNotExistException;

    UserResponseDTO findUserByEmail(String login) throws UsernameNotFoundException;

    User findUserEntityByEmail(String email) throws UsernameNotFoundException;

    UserResponseDTO createUser(UserRegistryRequestDTO registryRequest) throws UserAlreadyExistAuthenticationException;

    UserResponseDTO updateUserById(UUID id, UserUpdateRequestDTO updateRequest)
            throws UsernameNotFoundException, UserAlreadyExistAuthenticationException;

    void deleteUserById(UUID id);
}
