package com.globaljobsnepal.company.entity;

import com.globaljobsnepal.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/8/2026 4:34 PM
 * -------------------------------------------------------------
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vacancy extends BaseEntity<Long> {

    private String jobTitle;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String jobDescription;
    private String postedAt;
    private String location;
    private String companyName;
    private String url;
    private String applyBefore;
    private String vacancy;
    private String views;
}
