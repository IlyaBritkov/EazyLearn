package com.eazylearn.controller;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.security.jwt.JwtAuthenticationFacadeImpl;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.security.jwt.JwtUserDetailsService;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.ForbiddenException;
import java.util.List;
import java.util.UUID;

import static com.eazylearn.enums.UserRole.ROLE_ADMIN;
import static com.eazylearn.util.Constants.USERS_ENDPOINT_PATH;
import static com.eazylearn.util.Convertor.uuidToString;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = USERS_ENDPOINT_PATH)
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtAuthenticationFacadeImpl jwtAuthenticationFacade;
    private final JwtUserDetailsService jwtUserDetailsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
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
        final JwtUser currentUser = jwtAuthenticationFacade.getJwtPrincipal();
        if (currentUser.getId().equals(userId.toString())
                || jwtUserDetailsService.isUserHasAuthority(currentUser, ROLE_ADMIN.name())) {
            final User user = userService.findUserById(uuidToString(userId));

            return ok(userMapper.toResponseDTO(user));
        } else {
            throw new ForbiddenException();
        }

    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registry(@RequestBody UserRegistryRequestDTO requestDTO) {
        final User createdUser = userService.createUser(requestDTO);
        return ok(userMapper.toResponseDTO(createdUser));
    }

    // todo: update user info
    // todo: delete user
    // todo: reset password
}