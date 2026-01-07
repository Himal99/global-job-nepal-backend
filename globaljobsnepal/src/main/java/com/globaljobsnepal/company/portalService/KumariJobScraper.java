package com.globaljobsnepal.company.portalService;

import com.globaljobsnepal.dto.JobResponseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KumariJobScraper {

    public List<JobResponseDto> getJobs() {

        List<JobResponseDto> jobs = new ArrayList<JobResponseDto>();
        try {
            Document doc = Jsoup.connect("https://www.kumarijob.com/")
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Elements jobCards = doc.select("div.featured-job-card");

            for (Element card : jobCards) {

                String jobTitle = card.selectFirst("a.job-info") != null
                        ? card.selectFirst("a.job-info").text()
                        : "";

                String companyName = card.selectFirst("a.featured-job-company-name") != null
                        ? card.selectFirst("a.featured-job-company-name").text()
                        : "";

                String location = "";
                String daysLeft = "";

                Elements footerPs = card.select("div.featured-job-card-footer p");

                for (Element p : footerPs) {
                    String text = p.text().toLowerCase();

                    if (text.contains("day")) {
                        daysLeft = p.text();
                    } else {
                        location = p.text();
                    }
                }

                jobs.add(JobResponseDto.builder()
                                .jobTitle(jobTitle)
                                .companyName(companyName)
                                .location(location)
                                .applyBefore(daysLeft)
                        .build());

                System.out.println("Job Title : " + jobTitle);
                System.out.println("Company  : " + companyName);
                System.out.println("Location : " + location);
                System.out.println("Deadline : " + daysLeft);
                System.out.println("----------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }
}
