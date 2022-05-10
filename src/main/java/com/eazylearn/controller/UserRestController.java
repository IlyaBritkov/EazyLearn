package com.eazylearn.controller;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.security.jwt.JwtAuthenticationFacadeImpl;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.security.jwt.JwtUserDetailsService;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.eazylearn.enums.UserRole.ADMIN;
import static com.eazylearn.security.jwt.JwtUserDetailsService.isUserHasAuthority;
import static com.eazylearn.util.Constants.USERS_ENDPOINT_PATH;
import static com.eazylearn.util.Convertor.uuidToString;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = USERS_ENDPOINT_PATH)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class UserRestController { // todo: maybe endpoint reset password?

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtAuthenticationFacadeImpl jwtAuthenticationFacade;
    private final JwtUserDetailsService jwtUserDetailsService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        final List<User> allUsers = userService.findAllUsers();

        final List<UserResponseDTO> userResponseDTOs = allUsers.stream()
                .map(userMapper::toResponseDTO)
                .collect(toList());
        return ok(userResponseDTOs);
    }

    /**
     * Access to endpoint have only user with <b>ADMIN_ROLE</b> or current user (tenant)
     **/
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID userId) {
        // check if operation is allowed for current user
        final String id = uuidToString(userId);
        checkIfCurrentUserIdOrCurrentUserIsAdmin(id);

        final User user = userService.findUserById(id);
        return ok(userMapper.toResponseDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registry(@RequestBody UserRegistryRequestDTO requestDTO) {
        final User createdUser = userService.createUser(requestDTO);
        return ok(userMapper.toResponseDTO(createdUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable("id") UUID userId,
                                                          @RequestBody UserUpdateRequestDTO requestDTO) {
        // check if operation is allowed for current user
        final String id = uuidToString(userId);
        checkIfCurrentUserIdOrCurrentUserIsAdmin(id);

        final User updatedUser = userService.updateUserById(id, requestDTO);
        return ok(userMapper.toResponseDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public HeadersBuilder<?> deleteUserById(@PathVariable("id") UUID userId) {
        // check if operation is allowed for current user
        final String id = uuidToString(userId);
        checkIfCurrentUserIdOrCurrentUserIsAdmin(id);

        userService.deleteUserById(uuidToString(userId));
        return noContent();
    }

    private void checkIfCurrentUserIdOrCurrentUserIsAdmin(String userId) {
        final JwtUser currentUser = jwtAuthenticationFacade.getJwtPrincipal();
        if (!Objects.equals(userId, currentUser.getId())
                && !isUserHasAuthority(currentUser, ADMIN.toString())) {
            log.error("User with id={} hasn't permission to perform action", currentUser.getId());
            throw new AccessDeniedException(
                    String.format("User with id=%s hasn't permission to perform action", currentUser.getId()));
        }
    }
}