package com.globaljobsnepal.auth.loksewa.controller;

import com.globaljobsnepal.auth.loksewa.dto.LoksewaNotice;
import com.globaljobsnepal.auth.loksewa.service.LoksewNewsScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/9/2026 4:45 PM
 * -------------------------------------------------------------
 */

@RestController
@RequestMapping("v1/api/loksewa")
public class LoksewaController {

    private final LoksewNewsScraperService loksewNewsScraperService;

    public LoksewaController(LoksewNewsScraperService loksewNewsScraperService) {
        this.loksewNewsScraperService = loksewNewsScraperService;
    }

    @GetMapping("/notice")
    public ResponseEntity<?> getAllNew() throws Exception {
        List<LoksewaNotice> loksewaNotices = loksewNewsScraperService.scrapeNotices();

        return ResponseEntity.ok(loksewaNotices);
    }
}
