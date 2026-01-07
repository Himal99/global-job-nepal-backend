package com.globaljobsnepal.auth.repo;


import com.globaljobsnepal.auth.entity.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Himal Rai on 2/2/2024
 * Sb Solutions Nepal pvt.ltd
 * Project filecompressor-backend.
 */
@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Long> {
}
