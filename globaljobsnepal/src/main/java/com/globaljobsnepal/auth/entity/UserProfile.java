package com.globaljobsnepal.auth.entity;

import com.globaljobsnepal.auth.dto.converter.*;
import com.globaljobsnepal.company.entity.Vacancy;
import com.globaljobsnepal.core.entity.BaseEntity;
import com.globaljobsnepal.core.service.BaseService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 8:47 PM
 * -------------------------------------------------------------
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends BaseEntity<Long> {

    @Convert(converter = AddressConverter.class)
    private AddressDto address;

    private String phone;


    private String education;

    private String skills;

    @Convert(converter = ExperienceConverter.class)
    private List<ExperienceDto> experience;


    private String profilePicture;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String about;

    @OneToMany
    private List<Vacancy> vacancies;

    @Convert(converter = ProjectConverter.class)
    private List<ProjectDto> projects;

    private String resumeLinks;

    private String socialMediaLinks;

    private Long userId;

    @Transient
    private String firstName;
    @Transient
    private String lastName;

    @Transient
    private String email;
}
