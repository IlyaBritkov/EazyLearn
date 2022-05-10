package com.eazylearn.dto.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserAuthenticationRequestDTO {
    @ApiModelProperty(required = true, example = "user@gmail.com")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(required = true, example = "password123")
    @NotBlank
    @Length(min = 5, max = 30)
    private String password;
}
