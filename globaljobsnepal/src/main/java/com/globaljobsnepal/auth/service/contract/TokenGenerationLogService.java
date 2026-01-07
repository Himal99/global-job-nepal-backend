package com.globaljobsnepal.auth.service.contract;


import com.globaljobsnepal.auth.dto.response.TokenGenerationLogDto;
import com.globaljobsnepal.auth.entity.TokenGenerationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface  TokenGenerationLogService {

    TokenGenerationLog saveTokenLog(TokenGenerationLog tokenGenerationLog);
    Page<TokenGenerationLog> getByUserEmail(String userEmail, Pageable pageable);
    List<TokenGenerationLogDto> findAll();

}
