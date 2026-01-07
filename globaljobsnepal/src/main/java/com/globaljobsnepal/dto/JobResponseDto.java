package com.globaljobsnepal.dto;

import lombok.*;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/6/2026 11:18 AM
 * -------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {
    private String jobTitle;
    private String jobDescription;
    private String createdAt;
    private String location;
    private String companyName;
    private String url;
    private String applyBefore;
    private String vacancy;
    private String views;

    @Override
    public String toString() {
        return "JobResponseDto{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", location='" + location + '\'' +
                ", companyName='" + companyName + '\'' +
                ", url='" + url + '\'' +
                ", applyBefore='" + applyBefore + '\'' +
                '}';
    }
}
