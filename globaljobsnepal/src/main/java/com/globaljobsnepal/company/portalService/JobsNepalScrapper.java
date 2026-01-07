package com.globaljobsnepal.company.portalService;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/5/2026 4:10 PM
 * -------------------------------------------------------------
 */
@Service
public class JobsNepalScrapper {



    private static final String JOB_LIST_URL = "https://www.jobsnepal.com/jobs";

    public List<Map<String,Object>> getJobs() throws Exception {

        Document listDoc = Jsoup.connect(JOB_LIST_URL)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements jobCards = listDoc.select("div.card-inner"); // each job card
        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
        for (Element card : jobCards) {
            // Job title
            Element titleEl = card.selectFirst("h2.job-title a");
            String jobTitle = titleEl != null ? titleEl.text().trim() : "";
            String jobUrl = titleEl != null ? titleEl.attr("abs:href") : "";

            // Company
            Element companyEl = card.selectFirst("li:has(i.icon-briefcase3) p.mb-0");
            String company = companyEl != null ? companyEl.text().trim() : "";

            // Location
            Element locEl = card.selectFirst("li:has(i.icon-location4) div");
            String location = locEl != null ? locEl.text().trim() : "";

            // Initialize description and dates
            String description = "";
            String datePosted = "";
            String applyBefore = "";

            // Fetch job detail page for description and dates
            if (!jobUrl.isEmpty()) {
                try {
                    Document jobDoc = Jsoup.connect(jobUrl)
                            .userAgent("Mozilla/5.0")
                            .timeout(15000)
                            .get();

                    // Description
                    Element descEl = jobDoc.selectFirst("#div-job-details span[itemprop=description]");
                    if (descEl != null) {
                        String rawHtml = descEl.html();
                        String decoded = StringEscapeUtils.unescapeHtml4(rawHtml);
                        description = Jsoup.parse(decoded).text()
                                .replaceAll("[\\uFEFF\\u200B]", "")
                                .replaceAll("\\s+", " ")
                                .trim();
                    }

                    // Date posted
                    Element datePostedEl = jobDoc.selectFirst("meta[itemprop=datePosted]");
                    if (datePostedEl != null) {
                        datePosted = datePostedEl.attr("content").trim();
                    }

                    // Apply before
                    Element applyBeforeEl = jobDoc.selectFirst("meta[itemprop=validThrough]");
                    if (applyBeforeEl != null) {
                        applyBefore = applyBeforeEl.attr("content").trim();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Print all info
            System.out.println("Job Title: " + jobTitle);
            System.out.println("Company: " + company);
            System.out.println("Location: " + location);
            System.out.println("Description: " + description);
            System.out.println("Posted on: " + datePosted);
            System.out.println("Apply before: " + applyBefore);
            System.out.println("Apply URL: " + jobUrl);
            System.out.println("------------------------------------------------");

            maps.add(
                    Map.of("Job title",jobTitle,
                            "company",company,
                            "Location",location,
                            "Description",description,
                            "Posted On", datePosted,
                            "Apply before", applyBefore,
                            "Apply URL", jobUrl)
            );

            // polite delay
            Thread.sleep(1000);
        }

        return maps;
    }


}






