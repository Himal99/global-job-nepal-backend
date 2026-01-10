package com.globaljobsnepal.auth.dto.request;

import com.globaljobsnepal.auth.dto.converter.*;
import com.globaljobsnepal.company.entity.Vacancy;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 9:16 PM
 * -------------------------------------------------------------
 */

@Data
public class UserProfileReqDto {
    private String firstName;
    private String lastName;

    private String email;

    private Long userId;


    private AddressDto address;

    private String phone;
    private List<EducationDto> education;

    private String skills;


    private List<ExperienceDto> experience;


    private String profilePicture;


    private String about;


    private List<Vacancy> vacancies;


    private List<ProjectDto> projects;

    private String resumeLinks;

    private List<SocialMediaDto> socialMediaLinks;
}
