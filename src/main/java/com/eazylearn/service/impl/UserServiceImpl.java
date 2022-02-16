package com.eazylearn.service.impl;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import com.eazylearn.exception.UserAlreadyExistAuthenticationException;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.repository.RoleRepository;
import com.eazylearn.repository.UserRepository;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.eazylearn.enums.UserRole.USER;
import static com.eazylearn.enums.UserStatus.ACTIVE;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService { // todo: add current user

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id = %s doesn't exist", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with email = %s doesn't exist", email)));
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public User createUser(UserRegistryRequestDTO registryRequest) {
        final String email = registryRequest.getEmail();

        if (!userRepository.existsByEmail(email)) {
            User newUser = userMapper.toEntity(registryRequest);
            newUser.setPassword(passwordEncoder.encode(registryRequest.getPassword()));

            // todo: ADD CACHE for Roles and Authorities
            final Role userRole = roleRepository.findByName(USER)
                    .orElseGet(() -> new Role(USER));

            newUser.setStatus(ACTIVE);
            newUser.setRoles(new ArrayList<>(List.of(userRole)));

            User savedUser = userRepository.save(newUser);
            log.debug(String.format("User with email = %s was created", email));
            return savedUser;
        } else {
            log.error(String.format("User with email = %s already exists", email));
            throw new UserAlreadyExistAuthenticationException(
                    String.format("User with email = %s already exists", email));
        }
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public User updateUserById(String id, UserUpdateRequestDTO updateRequest)
            throws UsernameNotFoundException, UserAlreadyExistAuthenticationException {

        // todo: 6/8/2021 add check right for update
        User persistedUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id = %s doesn't exist", id)));

        String newEmail = updateRequest.getEmail();
        if (userRepository.existsByEmail(newEmail)) {
            throw new UserAlreadyExistAuthenticationException(
                    String.format("user with email = %s already exists", newEmail));
        }

        userMapper.updateEntity(updateRequest, persistedUser);

        return persistedUser;
    }

    @Override
    @Transactional
    public void deleteUserById(String id) {
        // todo: 6/8/2021 add check right for update

        userRepository.deleteById(id);
    }
}
