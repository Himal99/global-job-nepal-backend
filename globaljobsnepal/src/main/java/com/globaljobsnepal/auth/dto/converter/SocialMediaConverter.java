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
 * |   Created     : 1/10/2026 11:27 AM
 * -------------------------------------------------------------
 */

@Converter(autoApply = true)
public class SocialMediaConverter implements AttributeConverter<List<SocialMediaDto>, String> {
    @Override
    public String convertToDatabaseColumn(List<SocialMediaDto> socialMediaDtos) {
        String string = "";
        try {
            string = new ObjectMapper().writeValueAsString(socialMediaDtos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return string;
    }

    @Override
    public List<SocialMediaDto> convertToEntityAttribute(String string) {
        try {
            return new ObjectMapper().readValue(string, new ObjectMapper().getTypeFactory()
                    .constructCollectionType(List.class, SocialMediaDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
