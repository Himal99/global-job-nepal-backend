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
 * |   Created     : 1/10/2026 11:15 AM
 * -------------------------------------------------------------
 */
@Converter(autoApply = true)
public class EducationConverter implements AttributeConverter<List<EducationDto>, String> {

    @Override
    public String convertToDatabaseColumn(List<EducationDto> educationDtos) {
        String string = "";
        try {
            string = new ObjectMapper().writeValueAsString(educationDtos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return string;
    }

    @Override
    public List<EducationDto> convertToEntityAttribute(String string) {
        try {
            return new ObjectMapper().readValue(string, new ObjectMapper().getTypeFactory()
                    .constructCollectionType(List.class, EducationDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
