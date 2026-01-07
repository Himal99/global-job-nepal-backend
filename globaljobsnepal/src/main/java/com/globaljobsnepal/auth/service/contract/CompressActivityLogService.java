package com.globaljobsnepal.auth.service.contract;


import com.globaljobsnepal.auth.entity.CompressActivityLog;
import com.globaljobsnepal.core.enums.CompressStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompressActivityLogService {
    CompressActivityLog saveCompressLog(CompressActivityLog compressActivityLog);
    CompressActivityLog saveCompressLog(String email, CompressStatus compressStatus);
    Page<CompressActivityLog> getByUserEmail(String userEmail, Pageable pageable);
    Page<CompressActivityLog> findAllPageable(Pageable pageable);

}
