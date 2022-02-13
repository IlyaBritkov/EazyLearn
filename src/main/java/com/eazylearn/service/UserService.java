package com.eazylearn.service;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.entity.User;
import com.eazylearn.exception.EntityDoesNotExistException;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(String id) throws EntityDoesNotExistException;

    User findUserByEmail(String email);

    User createUser(UserRegistryRequestDTO registryRequest);

    User updateUserById(String id, UserUpdateRequestDTO updateRequest);

    void deleteUserById(String id);
}
