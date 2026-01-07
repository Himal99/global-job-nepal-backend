package com.globaljobsnepal.auth.service.implementation;

import com.globaljobsnepal.auth.entity.Role;
import com.globaljobsnepal.auth.enums.Roles;
import com.globaljobsnepal.auth.repo.RoleRepository;
import com.globaljobsnepal.auth.service.contract.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Himal Rai on 1/14/2024
 * Sb Solutions Nepal pvt.ltd
 * Project sb-back-core.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRole(Roles name) {
        Optional<Role> role = repository.findByName(name);
        return role.orElse(null);
    }
}
