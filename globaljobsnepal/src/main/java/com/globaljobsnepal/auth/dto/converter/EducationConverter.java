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
public class EducationConverter implements AttributeConverter<EducationDto, String> {
    @Override
    public String convertToDatabaseColumn(EducationDto educationDto) {
        return "";
    }

    @Override
    public EducationDto convertToEntityAttribute(String string) {
        try {
            return new ObjectMapper().readValue(string, new ObjectMapper().getTypeFactory()
                    .constructCollectionType(List.class, EducationDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
