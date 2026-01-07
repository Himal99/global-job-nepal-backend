package com.globaljobsnepal.auth.service.implementation;

import com.globaljobsnepal.auth.entity.UserConfiguration;
import com.globaljobsnepal.auth.repo.UserConfigurationRepository;
import com.globaljobsnepal.auth.service.contract.UserConfigurationService;

import com.globaljobsnepal.core.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Himal Rai on 2/2/2024
 * Sb Solutions Nepal pvt.ltd
 * Project filecompressor-backend.
 */
@Service
public class UserConfigurationImpl implements UserConfigurationService {

    private final UserConfigurationRepository userConfigurationRepository;

    public UserConfigurationImpl(UserConfigurationRepository userConfigurationRepository) {
        this.userConfigurationRepository = userConfigurationRepository;
    }

    @Override
    public UserConfiguration save(UserConfiguration userConfiguration) {
        return userConfigurationRepository.save(userConfiguration);
    }

    @Override
    public UserConfiguration findById(Long id) {

        return userConfigurationRepository.findById(id).orElseThrow(() -> new CustomException("Could not find configuration"));
    }

    @Override
    public List<UserConfiguration> findAll() {
        return userConfigurationRepository.findAll();
    }
}
