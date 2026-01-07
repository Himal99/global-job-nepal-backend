package com.globaljobsnepal.auth.service.implementation;

import com.globaljobsnepal.auth.dto.response.TokenGenerationLogDto;
import com.globaljobsnepal.auth.entity.TokenGenerationLog;
import com.globaljobsnepal.auth.repo.TokenGenerationLogRepository;
import com.globaljobsnepal.auth.service.contract.TokenGenerationLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenGenerationLogServiceImpl implements TokenGenerationLogService {
    private final TokenGenerationLogRepository tokenGenerationLogRepository;

    public TokenGenerationLogServiceImpl(TokenGenerationLogRepository tokenGenerationLogRepository) {
        this.tokenGenerationLogRepository = tokenGenerationLogRepository;
    }

    @Override
    public TokenGenerationLog saveTokenLog(TokenGenerationLog tokenGenerationLog) {
        return tokenGenerationLogRepository.save(tokenGenerationLog);
    }

    @Override
    public Page<TokenGenerationLog> getByUserEmail(String email, Pageable pageable) {
        return tokenGenerationLogRepository.getByUserEmail(email, pageable);
    }

    @Override
    public List<TokenGenerationLogDto> findAll() {
        List<TokenGenerationLog> allUserToken = tokenGenerationLogRepository.findAll();
        // Group the tokens by username and count the tokens for each user
        Map<String, Long> tokenCountByUser;
        tokenCountByUser = allUserToken
                .stream()
                .collect(Collectors.groupingBy(log -> log.getUser().getUsername(), Collectors.counting()));
        List<TokenGenerationLogDto> tokenGenerationLogs = new ArrayList<>();

//        for (Map.Entry<String, Long> entry : tokenCountByUser.entrySet()) {
//            TokenGenerationLogDto log = new TokenGenerationLogDto(entry.getKey(), entry.getValue(), );
//            tokenGenerationLogs.add(log);
//        }
        return tokenGenerationLogs;
    }

}
