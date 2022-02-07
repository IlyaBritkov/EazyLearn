package com.eazylearn.controller;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import com.eazylearn.mapper.UserMapper;
import com.eazylearn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("registry")
    public ResponseEntity<UserResponseDTO> registry(@RequestBody UserRegistryRequestDTO requestDTO) {
        final User createdUser = userService.createUser(requestDTO);
        return ResponseEntity.ok(userMapper.toResponseDTO(createdUser));
    }

}
