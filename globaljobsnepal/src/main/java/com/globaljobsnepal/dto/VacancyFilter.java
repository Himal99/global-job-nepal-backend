package com.globaljobsnepal.dto;

import lombok.Data;

@Data
public class VacancyFilter {
    private String keyword;    // Search in jobTitle, jobDescription, companyName
    private String location;   // Location filter
    private String jobType;    // Full Time / Part Time / Remote etc.
    private Integer minSalary; // Minimum salary
    private Integer maxSalary; // Maximum salary
    private Boolean isNew;     // New jobs only
}
