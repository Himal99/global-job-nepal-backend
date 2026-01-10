package com.globaljobsnepal.auth.dto.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 8:51 PM
 * -------------------------------------------------------------
 */

@Converter(autoApply = true)
public class AddressConverter implements AttributeConverter<AddressDto, String> {


    @Override
    public String convertToDatabaseColumn(AddressDto addressDto) {
        try {
            return new ObjectMapper().writeValueAsString(addressDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AddressDto convertToEntityAttribute(String string) {
        try {
           return new ObjectMapper().readValue(string,AddressDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
