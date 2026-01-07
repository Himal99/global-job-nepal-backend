package com.globaljobsnepal.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenGenerationLogDto {
    private String email;
    private Long count;
    private String userName;
}
