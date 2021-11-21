package com.eazylearn.dto.request.user;

import lombok.Data;

@Data
public class UserUpdateRequestDTO { // TODO: 6/8/2021 add validation

    // ? maybe fields are nullable
    private String username;
    private String email;
    private String password;

}
