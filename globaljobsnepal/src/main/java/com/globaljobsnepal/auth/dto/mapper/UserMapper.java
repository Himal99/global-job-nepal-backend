package com.globaljobsnepal.auth.dto.mapper;

import com.globaljobsnepal.auth.dto.request.UserRegisterRequest;
import com.globaljobsnepal.auth.entity.User;
import com.globaljobsnepal.auth.entity.UserConfiguration;
import com.globaljobsnepal.auth.repo.UserRepository;
import com.globaljobsnepal.exception.CustomException;
import com.globaljobsnepal.utils.AppUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Himal Rai on 1/14/2024
 * Sb Solutions Nepal pvt.ltd
 * Project sb-back-core.
 */

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final AppUtils appUtils;

    private final UserRepository userRepository;


    public UserMapper(PasswordEncoder passwordEncoder, AppUtils appUtils, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUtils = appUtils;
        this.userRepository = userRepository;
    }


    public User convertToUser(UserRegisterRequest userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setServerCompressor(userDto.getServerCompressor());
        user.setPhoneNumber(userDto.getPhoneNumber());


        if (user.getId() == null) {
            // add
            user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(appUtils.assignRoleToUser(userDto.getRole()));
            user.setUserConfiguration(new UserConfiguration());
        } else {
            //edit
            User existingUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new CustomException("User not found"));
            user.setRoles(appUtils.assignRoleToUser(userDto.getRole()));
            user.setPassword(existingUser.getEncodedPassword());
        }


        return user;
    }
}
