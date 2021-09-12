package com.eazylearn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserAuthenticationResponseDTO {

    private UUID id;
    private String email;
    private String token;

}
