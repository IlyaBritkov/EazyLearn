package com.eazylearn.service;

import com.eazylearn.dto.request.UserRegistryRequestDTO;
import com.eazylearn.dto.request.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.exception.UserAlreadyExistAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> findAllUsers();

    UserResponseDTO findUserById(Long id) throws EntityDoesNotExistException;

    UserResponseDTO findUserByEmail(String login) throws UsernameNotFoundException;

    User findUserEntityByEmail(String email) throws UsernameNotFoundException;

    UserResponseDTO createUser(UserRegistryRequestDTO registryRequest) throws UserAlreadyExistAuthenticationException;

    UserResponseDTO updateUserById(Long id, UserUpdateRequestDTO updateRequest) throws UsernameNotFoundException, UserAlreadyExistAuthenticationException;

    void deleteUserById(Long id);
}
