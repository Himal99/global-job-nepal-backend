package com.globaljobsnepal.auth.repo;

import com.globaljobsnepal.auth.entity.TokenGenerationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenGenerationLogRepository extends JpaRepository<TokenGenerationLog, Long> {

    @Query("SELECT token FROM TokenGenerationLog token WHERE token.user.email = :email")
    Page<TokenGenerationLog> getByUserEmail(@Param("email")String email, Pageable pageable);





}
