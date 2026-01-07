package com.globaljobsnepal.auth.service.contract;


import com.globaljobsnepal.auth.entity.Role;
import com.globaljobsnepal.auth.enums.Roles;

/**
 * @author Himal Rai on 1/14/2024
 * Sb Solutions Nepal pvt.ltd
 * Project sb-back-core.
 */
public interface RoleService {
    Role getRole(Roles name);
}
