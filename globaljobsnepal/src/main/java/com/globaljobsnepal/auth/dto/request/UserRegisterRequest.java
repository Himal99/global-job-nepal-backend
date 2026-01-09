package com.globaljobsnepal.auth.dto.request;

import com.globaljobsnepal.auth.enums.UserType;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Himal Rai on 1/14/2024
 * Sb Solutions Nepal pvt.ltd
 * Project sb-back-core.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private Long id;

    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotBlank(message = "First Name can't be empty")
    private String firstName;

    @NotBlank(message = "Last Name can't be empty")
    private String lastName;

    @NotBlank(message = "Password can't be empty")

    private String password;
    private Set<String> role;

    private String phoneNumber;

    private Boolean serverCompressor;

    @NotNull
    private UserType userType;
}
