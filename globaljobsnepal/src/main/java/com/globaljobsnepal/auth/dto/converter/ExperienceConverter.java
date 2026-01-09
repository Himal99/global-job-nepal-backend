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
 * |   Created     : 1/9/2026 8:58 PM
 * -------------------------------------------------------------
 */

@Converter(autoApply = true)
public class ExperienceConverter implements AttributeConverter<List<ExperienceDto>, String> {
    @Override
    public String convertToDatabaseColumn(List<ExperienceDto> experienceDtos) {
        String string = "";
        try {
            string = new ObjectMapper().writeValueAsString(experienceDtos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return string;
    }

    @Override
    public List<ExperienceDto> convertToEntityAttribute(String string) {

        try {
           return new ObjectMapper().readValue(string, new ObjectMapper().getTypeFactory()
                    .constructCollectionType(List.class, ExperienceDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
