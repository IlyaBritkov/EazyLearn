package com.eazylearn.dto.request;

import lombok.Data;

@Data
public class UserRegistryRequestDTO { // TODO: 6/8/2021 add validation
    private String nickname;
    private String email;
    private String password;
}
