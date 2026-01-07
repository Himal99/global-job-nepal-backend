package com.globaljobsnepal.controller;

import com.globaljobsnepal.company.portalService.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/5/2026 4:46 PM
 * -------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/jobs/nepal")
public class JobsNepalController {
    private final JobsNepalScrapper jobsNepalScrapper;
    private final MerojobFullScraper merojobFullScraper;
    private final KumariJobScraper kumariJobScraper;
    private final MeroRojgariScraper meroRojgariScraper;
    private final JobJeeScrapper jobJeeScrapper;

    public JobsNepalController(JobsNepalScrapper jobsNepalScrapper, MerojobFullScraper merojobFullScraper, KumariJobScraper kumariJobScraper, MeroRojgariScraper meroRojgariScraper, JobJeeScrapper jobJeeScrapper) {
        this.jobsNepalScrapper = jobsNepalScrapper;
        this.merojobFullScraper = merojobFullScraper;
        this.kumariJobScraper = kumariJobScraper;
        this.meroRojgariScraper = meroRojgariScraper;
        this.jobJeeScrapper = jobJeeScrapper;
    }

    @GetMapping
    public ResponseEntity<?> getJobsNepalData() throws Exception {
        return
                ResponseEntity.ok(
                        Map.of("JoobJee", jobJeeScrapper.getJobs(),
                                "kumari",kumariJobScraper.getJobs(),
                                "meroRojgari",meroRojgariScraper.getJobs(),
                                "jobsNepal", jobsNepalScrapper.getJobs(),
                                "meroJob",merojobFullScraper.getJobs())
                );
    }
}
