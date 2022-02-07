package com.eazylearn.service;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.exception.UserAlreadyExistAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService { // todo: refactor

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findUserById(UUID id) throws EntityDoesNotExistException;

    Optional<User> findUserByEmail(String email) throws UsernameNotFoundException;

    User createUser(UserRegistryRequestDTO registryRequest) throws UserAlreadyExistAuthenticationException;

    UserResponseDTO updateUserById(UUID id, UserUpdateRequestDTO updateRequest)
            throws UsernameNotFoundException, UserAlreadyExistAuthenticationException;

    void deleteUserById(UUID id);
}
