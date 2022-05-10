package com.eazylearn.dto.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistryRequestDTO { // todo: add proper validation for password
    @ApiModelProperty(required = true, example = "just_ilusssh")
    @NotBlank
    private String username;

    @ApiModelProperty(required = true, example = "example@gmail.com")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank
    private String password;
}
