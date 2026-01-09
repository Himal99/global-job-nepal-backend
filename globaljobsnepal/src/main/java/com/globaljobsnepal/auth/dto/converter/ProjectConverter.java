package com.globaljobsnepal.auth.dto.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 9:07 PM
 * -------------------------------------------------------------
 */

@Converter(autoApply = true)
public class ProjectConverter implements AttributeConverter<List<ProjectDto>, String> {
    @Override
    public String convertToDatabaseColumn(List<ProjectDto> projectDtos) {
        String string = "";
        try {
            string = new ObjectMapper().writeValueAsString(projectDtos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return string;
    }


    @Override
    public List<ProjectDto> convertToEntityAttribute(String string) {
        try {
            return new ObjectMapper().readValue(string, new ObjectMapper().getTypeFactory()
                    .constructCollectionType(List.class, ProjectDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
