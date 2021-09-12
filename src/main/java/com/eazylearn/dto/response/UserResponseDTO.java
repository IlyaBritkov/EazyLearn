package com.eazylearn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserResponseDTO {

    @EqualsAndHashCode.Include
    private UUID id;

    private String username;

    private String email;

    private String status;

    private String role;

}
