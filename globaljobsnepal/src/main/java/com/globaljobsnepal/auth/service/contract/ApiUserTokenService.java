package com.globaljobsnepal.auth.service.contract;


import com.globaljobsnepal.auth.dto.request.ApiUserTokenRequestDto;
import com.globaljobsnepal.auth.dto.response.TokenGenerationLogDto;
import com.globaljobsnepal.auth.entity.ApiUserToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApiUserTokenService {
    ApiUserToken generateToken(ApiUserToken apiUserToken);
    ApiUserToken generateTokenForApiUser(ApiUserToken apiUserToken);
    ApiUserToken getTokenById(Long id);
    List<TokenGenerationLogDto> getAllToken();
    ApiUserToken updateToken(ApiUserTokenRequestDto apiUserTokenRequestDto);
    String delete(Long id);
    Page<ApiUserToken> findPageable(Pageable pageable);
    List<ApiUserToken> getByUser(String username);
    Page<ApiUserToken> findPageableByEmail(String email, Pageable pageable);
    Optional<ApiUserToken> getByTokenValue(String token);
}
