package com.globaljobsnepal.controller;

import com.globaljobsnepal.company.entity.Vacancy;
import com.globaljobsnepal.company.portalService.*;
import com.globaljobsnepal.company.service.VacancyService;
import com.globaljobsnepal.core.exception.ApiResponse;
import com.globaljobsnepal.dto.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("v1/api/jobs/nepal")
public class JobsNepalController {
    private final JobsNepalScrapper jobsNepalScrapper;
    private final MerojobFullScraper merojobFullScraper;
    private final KumariJobScraper kumariJobScraper;
    private final MeroRojgariScraper meroRojgariScraper;
    private final JobJeeScrapper jobJeeScrapper;

    private final VacancyService vacancyService;

    public JobsNepalController(JobsNepalScrapper jobsNepalScrapper, MerojobFullScraper merojobFullScraper, KumariJobScraper kumariJobScraper, MeroRojgariScraper meroRojgariScraper, JobJeeScrapper jobJeeScrapper, VacancyService vacancyService) {
        this.jobsNepalScrapper = jobsNepalScrapper;
        this.merojobFullScraper = merojobFullScraper;
        this.kumariJobScraper = kumariJobScraper;
        this.meroRojgariScraper = meroRojgariScraper;
        this.jobJeeScrapper = jobJeeScrapper;
        this.vacancyService = vacancyService;
    }

    @GetMapping
    public ResponseEntity<?> getJobsNepalData() throws Exception {
        return
                ResponseEntity.ok(
                        Map.of("JoobJee", jobJeeScrapper.getJobs())
                );
    }

    @GetMapping("/dump")
    public ResponseEntity<?> dumpJobs() throws Exception {
//        this.jobJeeScrapper.getJobs();
        return
         ApiResponse.success(vacancyService.findAll());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getJobs(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.success(vacancyService.getJobs(search, pageable));
    }


}
