package com.globaljobsnepal.company.portalService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.globaljobsnepal.company.entity.Vacancy;
import com.globaljobsnepal.company.service.VacancyService;
import com.globaljobsnepal.dto.JobResponseDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/5/2026 5:24 PM
 * -------------------------------------------------------------
 */

@Service
public class JobJeeScrapper {

    private final VacancyService vacancyService;

    public JobJeeScrapper(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    public List<Vacancy> getJobs() throws InterruptedException {



        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--disable-gpu"); // Optional, often needed
        options.addArguments("--window-size=1920,1080");

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        List<JobResponseDto> jobResponseDtos = new ArrayList<JobResponseDto>();
        try {
            // Main job listing page
            String url = "https://www.jobejee.com"; // replace with actual URL
            driver.get(url);
            Thread.sleep(3000); // wait for JS

            Document doc = Jsoup.parse(driver.getPageSource());

            // Select all jobs
            Elements jobBlocks = doc.select("div.title");

            for (Element jobBlock : jobBlocks) {
                Element jobLink = jobBlock.selectFirst("div.sub-title a");
                if (jobLink == null) continue;

                String jobTitle = jobLink.text().trim();
                String jobUrl = "https://www.jobejee.com" + jobLink.attr("href");

                // Go inside job page
                driver.get(jobUrl);
                Thread.sleep(3000); // wait for JS

                Document jobDoc = Jsoup.parse(driver.getPageSource());
                String vacancy="";
                String location="";
                String views="";
                // 1️ Job details: vacancy, location, views
                Element details = jobDoc.selectFirst("div.pp-details");
                if (details != null) {
                     vacancy = details.select("p:contains(Vacancy) span.txt-grey").text();
                    Elements locationSpans = details.select("p:contains(Location) span.txt-grey");
                     location = locationSpans.eachText().toString()
                            .replace("[", "").replace("]", "").replace(",", ", ");
                     views = details.select("p:contains(Job Views) span.txt-grey").text();


                }
                String jobDesc="";
                // 2️ Job description
                Element descBox = jobDoc.selectFirst("div.row.overview_box");
                if (descBox != null) {
                    // Extract all text inside description
                     jobDesc = descBox.text().trim();
                    System.out.println("Job Description: ");
                    System.out.println(jobDesc);
                } else {
                    System.out.println("Job Description not found");
                }

                jobResponseDtos.add(
                        JobResponseDto.builder()
                                .jobTitle(jobTitle)
                                .jobDescription(jobDesc)
                                .url(jobUrl)
                                .location(location)
                                .vacancy(vacancy)
                                .views(views)
                                .build()
                );
                System.out.println("--------------------------------------------------");
            }

        } finally {
            driver.quit();
        }

        List<Vacancy> vacancies =

                new ObjectMapper().convertValue(jobResponseDtos, new ObjectMapper()
                        .getTypeFactory().constructCollectionType(List.class, Vacancy.class));

        vacancyService.saveAllInBatches(vacancies);

        return vacancies;
    }
}
