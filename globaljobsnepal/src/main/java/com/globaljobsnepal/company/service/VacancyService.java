package com.globaljobsnepal.company.service;

import com.globaljobsnepal.company.entity.Vacancy;
import com.globaljobsnepal.company.repo.VacancyRepository;
import com.globaljobsnepal.core.service.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/8/2026 4:36 PM
 * -------------------------------------------------------------
 */

@Service
public class VacancyService implements BaseService<Vacancy> {
    private final VacancyRepository vacancyRepository;

    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public Vacancy findOne(Long id) {
        return vacancyRepository.findById(id).orElse(null);
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Page<Vacancy> findAllPageable(Object t, Pageable pageable) {
        return vacancyRepository.findAll(pageable);
    }

    @Override
    public List<Vacancy> saveAll(List<Vacancy> list) {
        return vacancyRepository.saveAll(list);
    }

    @Async
    @Transactional
    public void saveAllInBatches(List<Vacancy> vacancies) {

        int batchSize = 500; // safe size (300–1000)
        List<Vacancy> batch = new ArrayList<>(batchSize);

        for (int i = 0; i < vacancies.size(); i++) {
            batch.add(vacancies.get(i));

            if (batch.size() == batchSize) {
                vacancyRepository.saveAll(batch);
                vacancyRepository.flush();   // push to DB
                batch.clear();               // free memory
            }
        }

        // save remaining
        if (!batch.isEmpty()) {
            vacancyRepository.saveAll(batch);
            vacancyRepository.flush();
        }
    }

    public Page<Vacancy> getJobs(String search, Pageable pageable) {
        return vacancyRepository
                .findByJobTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
                        search, search, pageable);
    }
}
