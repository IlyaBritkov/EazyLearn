package com.eazylearn.dto.response;

import com.eazylearn.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserResponseDTO { // todo: maybe add more fields (createdDateTime, updatedDateTime)

    @EqualsAndHashCode.Include
    private UUID id;

    private String username;

    private String email;

    private String status;

    private List<UserRole> roles;
}
