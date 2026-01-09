package com.globaljobsnepal.auth.dto.converter;

import lombok.Data;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 8:51 PM
 * -------------------------------------------------------------
 */

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
