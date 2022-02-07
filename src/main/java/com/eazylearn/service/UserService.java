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

public interface UserService { // todo: refactor

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findUserById(String id) throws EntityDoesNotExistException;

    Optional<User> findUserByEmail(String email) throws UsernameNotFoundException;

    User createUser(UserRegistryRequestDTO registryRequest) throws UserAlreadyExistAuthenticationException;

    UserResponseDTO updateUserById(String id, UserUpdateRequestDTO updateRequest)
            throws UsernameNotFoundException, UserAlreadyExistAuthenticationException;

    void deleteUserById(String id);
}
