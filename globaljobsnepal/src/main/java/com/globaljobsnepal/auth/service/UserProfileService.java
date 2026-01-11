package com.globaljobsnepal.auth.service;

import com.globaljobsnepal.auth.dto.converter.EducationDto;
import com.globaljobsnepal.auth.dto.converter.ExperienceDto;
import com.globaljobsnepal.auth.dto.request.UserProfileReqDto;
import com.globaljobsnepal.auth.entity.User;
import com.globaljobsnepal.auth.entity.UserProfile;
import com.globaljobsnepal.auth.repo.UserProfileRepo;
import com.globaljobsnepal.auth.service.contract.UserService;
import com.globaljobsnepal.core.service.BaseService;
import com.globaljobsnepal.exception.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 9:11 PM
 * -------------------------------------------------------------
 */

@Service
public class UserProfileService implements BaseService<UserProfile> {

    private final UserProfileRepo profileRepo;
    private final UserService userService;

    private final ObjectMapper objectMapper;
    private final UserProfileRepo userProfileRepo;

    public UserProfileService(UserProfileRepo profileRepo, UserService userService, ObjectMapper objectMapper, UserProfileRepo userProfileRepo) {
        this.profileRepo = profileRepo;
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.userProfileRepo = userProfileRepo;
    }


    public User updateUserProfile(UserProfileReqDto profileReqDto) {
        User user = userService.findByEmail(profileReqDto.getEmail());
        if (user == null) {
            throw new CustomException("User not found");
        }

        Long id = user.getId();


        UserProfile userProfile = objectMapper.convertValue(profileReqDto, UserProfile.class);

        userProfile.setUserId(id);
        userProfileRepo.findByUserId(id)
                .ifPresent(profile -> userProfile.setId(profile.getId()));

        UserProfile profile = save(userProfile);

        user.setUserDetails(profile);
        user.setFirstName(profileReqDto.getFirstName());
        user.setLastName(profileReqDto.getLastName());

        return userService.save(user);
    }

    public Optional<UserProfile> findByEmail(String email) {
        User user = userService.findByEmail(email);
        Optional<UserProfile> userProfile = userProfileRepo.findByUserId(user.getId());
        userProfile.ifPresent(profile -> {
            profile.setFirstName(user.getFirstName());
            profile.setLastName(user.getLastName());
            profile.setEmail(user.getEmail());
        });

        return userProfile;
    }


    @Override
    public List<UserProfile> findAll() {
        return profileRepo.findAll();
    }

    @Override
    public UserProfile findOne(Long id) {
        return profileRepo.findById(id).orElse(null);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return profileRepo.save(userProfile);
    }

    @Override
    public Page<UserProfile> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<UserProfile> saveAll(List<UserProfile> list) {
        return profileRepo.saveAll(list);
    }

    @Transactional
    public Object updateSection(String section, Object obj, String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new CustomException("User not found");
        }

        Long id = user.getId();


        switch (section) {
            case "ABOUT": {

                Map<String,Object> map = new ObjectMapper().convertValue(obj, Map.class);

                UserProfile userProfile = userProfileRepo.findByUserId(id)
                        .orElseThrow(() -> new CustomException("User not found"));
                userProfile.setAbout(map.getOrDefault("data","").toString());
                return save(userProfile);

            }
            case "EDUCATION": {

                List<EducationDto> educations = new ObjectMapper()
                        .convertValue(obj, new ObjectMapper()
                                .getTypeFactory()
                                .constructCollectionType(List.class, EducationDto.class));

                UserProfile userProfile = userProfileRepo.findByUserId(id)
                        .orElseThrow(() -> new CustomException("User not found"));
                userProfile.setEducation(educations);
                return save(userProfile);
            }

            case "EXPERIENCE": {
                List<ExperienceDto> educations = new ObjectMapper()
                        .convertValue(obj, new ObjectMapper()
                                .getTypeFactory()
                                .constructCollectionType(List.class, ExperienceDto.class));

                UserProfile userProfile = userProfileRepo.findByUserId(id)
                        .orElseThrow(() -> new CustomException("User not found"));
                userProfile.setExperience(educations);
                return save(userProfile);
            }
            default:
                return null;
        }

    }
}
