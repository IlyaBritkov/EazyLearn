package com.eazylearn.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequestDTO { // TODO: 6/8/2021 add validation

    private String username;
    private String email;
    private String password;

}
