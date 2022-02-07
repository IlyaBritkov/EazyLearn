package com.eazylearn.dto.request.user;

import lombok.Data;

@Data
public class UserAuthenticationRequestDTO { // todo: 6/8/2021 add validation
    private String email;
    private String password;
}
