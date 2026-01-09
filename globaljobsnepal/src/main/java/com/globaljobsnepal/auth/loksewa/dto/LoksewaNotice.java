package com.globaljobsnepal.auth.loksewa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoksewaNotice {

    private String title;
    private String pdfUrl;
    private String iconUrl;

    public LoksewaNotice(String title, String pdfUrl, String iconUrl) {
        this.title = title;
        this.pdfUrl = pdfUrl;
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "LoksewaNotice{" +
                "title='" + title + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
