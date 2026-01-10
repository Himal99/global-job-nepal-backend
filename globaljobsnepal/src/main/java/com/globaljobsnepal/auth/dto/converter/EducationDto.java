package com.globaljobsnepal.auth.dto.converter;

import lombok.Data;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/10/2026 11:08 AM
 * -------------------------------------------------------------
 */
@Data
public class EducationDto {
    private String collegeName;
    private String faculty;
    private String startDate;
    private String endDate;
    private boolean running;
    private DegreeType degreeType;



    public  enum DegreeType{
        PLUS_2,BACHELOR,MASTER,PHD,DIPLOMA,OTHER
    }
}


