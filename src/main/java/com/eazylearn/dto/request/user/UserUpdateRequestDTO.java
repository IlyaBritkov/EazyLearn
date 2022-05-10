package com.eazylearn.dto.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class UserUpdateRequestDTO {
    @ApiModelProperty
    @Nullable
    private String username;

    @ApiModelProperty(example = "example@gmail.com")
    @Nullable
    private String email;

    @ApiModelProperty
    @Nullable
    private String password;
}
