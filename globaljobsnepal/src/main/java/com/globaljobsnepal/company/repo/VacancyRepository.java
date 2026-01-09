package com.globaljobsnepal.company.repo;

import com.globaljobsnepal.company.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/8/2026 4:36 PM
 * -------------------------------------------------------------
 */
public interface VacancyRepository extends JpaRepository<Vacancy,Long> {
    Page<Vacancy> findByJobTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title, String location, Pageable pageable);

    @Query("SELECT v FROM Vacancy v " +
            "WHERE (:keyword IS NULL OR LOWER(v.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "       OR LOWER(v.jobDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "       OR LOWER(v.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:location IS NULL OR LOWER(v.location) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    Page<Vacancy> searchVacancies(
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("jobType") String jobType,
            @Param("minSalary") Integer minSalary,
            @Param("maxSalary") Integer maxSalary,
            @Param("isNew") Boolean isNew,
            Pageable pageable
    );
}
