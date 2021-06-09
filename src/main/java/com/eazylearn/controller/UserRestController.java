package com.eazylearn.controller;

import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping(value = "/api/v1/admin/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
