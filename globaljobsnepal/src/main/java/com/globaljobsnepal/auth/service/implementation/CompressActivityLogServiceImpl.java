package com.globaljobsnepal.auth.service.implementation;

import com.globaljobsnepal.auth.entity.CompressActivityLog;
import com.globaljobsnepal.auth.entity.User;
import com.globaljobsnepal.auth.repo.CompressActivityLogRepository;
import com.globaljobsnepal.auth.service.contract.CompressActivityLogService;
import com.globaljobsnepal.auth.service.contract.UserService;
import com.globaljobsnepal.core.enums.CompressStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CompressActivityLogServiceImpl implements CompressActivityLogService {

    private final CompressActivityLogRepository compressActivityLogRepository;
    private final UserService userService;

    public CompressActivityLogServiceImpl(CompressActivityLogRepository compressActivityLogRepository, UserService userService) {
        this.compressActivityLogRepository = compressActivityLogRepository;
        this.userService = userService;
    }

    @Override
    public CompressActivityLog saveCompressLog(CompressActivityLog compressActivityLog) {
        return compressActivityLogRepository.save(compressActivityLog);
    }

    @Override
    public CompressActivityLog saveCompressLog(String email, CompressStatus compressStatus) {
        User currentUser = userService.findByEmail(email);
        CompressActivityLog compressActivityLog = new CompressActivityLog().builder()
                .user(currentUser)
                .status(compressStatus)
                .build();
        return compressActivityLogRepository.save(compressActivityLog);
    }

    @Override
    public Page<CompressActivityLog> getByUserEmail(String userEmail, Pageable pageable) {
        return compressActivityLogRepository.getByUserEmail(userEmail, pageable);
    }

    @Override
    public Page<CompressActivityLog> findAllPageable(Pageable pageable) {
        return compressActivityLogRepository.findAll(pageable);
    }
}
