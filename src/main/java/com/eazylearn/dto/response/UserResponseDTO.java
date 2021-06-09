package com.eazylearn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserResponseDTO {
    @EqualsAndHashCode.Include
    private Long id;

    private String nickname;

    private String email;

    private Integer roleId;
}
