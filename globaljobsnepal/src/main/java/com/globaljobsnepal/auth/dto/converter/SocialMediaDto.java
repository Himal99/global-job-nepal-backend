package com.globaljobsnepal.auth.dto.converter;

import lombok.Data;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/10/2026 11:25 AM
 * -------------------------------------------------------------
 */

@Data
public class SocialMediaDto {
    private String link;
    private MediaType mediaType;

    public enum MediaType {
        FB,INSTAGRAM,LINKEDIN,YOUTUBE,GITHUB,TWITTER
    }
}
