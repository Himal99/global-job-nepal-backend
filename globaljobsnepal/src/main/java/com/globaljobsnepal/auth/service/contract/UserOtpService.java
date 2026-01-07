package com.globaljobsnepal.auth.service.contract;


import com.globaljobsnepal.auth.entity.UserOtp;
import com.globaljobsnepal.core.service.BaseService;

/**
 * @author Himal Rai on 2/22/2024
 * Sb Solutions Nepal pvt.ltd
 * Project filecompressor-backend.
 */
public interface UserOtpService extends BaseService<UserOtp> {
    UserOtp findByEmail(String email);
    void delete(UserOtp userOtp);
}
