package com.eazylearn.service.impl;

import com.eazylearn.dto.request.UserRegistryRequestDTO;
import com.eazylearn.dto.request.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import com.eazylearn.exception.UserAlreadyExistAuthenticationException;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.repository.UserRepository;
import com.eazylearn.service.RoleService;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.eazylearn.enums.UserStatus.ACTIVE;
import static java.util.stream.Collectors.toList;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder = passwordEncoder();

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(userMapper::toResponseDTO)
                .collect(toList());
    }

    @Override
    public UserResponseDTO findUserById(Long id) throws UsernameNotFoundException {
        User userById = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id = %d doesn't exist", id)));

        return userMapper.toResponseDTO(userById);
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) throws UsernameNotFoundException {
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email = %s doesn't exist", email)));

        return userMapper.toResponseDTO(userByEmail);
    }

    @Override
    public User findUserEntityByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email = %s doesn't exist", email)));
    }

    @Override
    public UserResponseDTO createUser(UserRegistryRequestDTO registryRequest) throws UserAlreadyExistAuthenticationException {
        String email = registryRequest.getEmail();
        if (!userRepository.existsByEmail(email)) {
            User newUser = userMapper.toEntity(registryRequest);
            newUser.setPassword(passwordEncoder.encode(registryRequest.getPassword()));

            @SuppressWarnings("OptionalGetWithoutIsPresent")
            Role userDefaultRole = roleService.findRoleByName("USER").get();

            newUser.setRole(userDefaultRole);
            newUser.setStatus(ACTIVE);

            User persistedUser = userRepository.save(newUser);

            return userMapper.toResponseDTO(persistedUser);
        } else {
            throw new UserAlreadyExistAuthenticationException(String.format("User with email = %s already exists", email));
        }
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public UserResponseDTO updateUserById(Long id, UserUpdateRequestDTO updateRequest) throws UsernameNotFoundException, UserAlreadyExistAuthenticationException {
        // TODO: 6/8/2021 add check right for update
        User persistedUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id = %d doesn't exist", id)));

        String newEmail = updateRequest.getEmail();
        if (userRepository.existsByEmail(newEmail)) {
            throw new UserAlreadyExistAuthenticationException(String.format("user with email = %s already exists", newEmail));
        }

        userMapper.updateEntity(updateRequest, persistedUser);

        return userMapper.toResponseDTO(persistedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
