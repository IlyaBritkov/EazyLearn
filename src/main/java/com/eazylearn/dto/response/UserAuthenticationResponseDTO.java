package com.eazylearn.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthenticationResponseDTO {
    private Long id;
    private String email;
    private String token;
}
