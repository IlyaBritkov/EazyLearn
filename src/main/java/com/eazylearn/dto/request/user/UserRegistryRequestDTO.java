package com.eazylearn.dto.request.user;

import lombok.Data;

@Data
public class UserRegistryRequestDTO { // TODO: 6/8/2021 add validation

    private String username;
    private String email;
    private String password;

}
