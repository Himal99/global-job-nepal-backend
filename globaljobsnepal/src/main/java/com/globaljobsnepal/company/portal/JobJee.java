package com.globaljobsnepal.company.portal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * -------------------------------------------------------------
 * |   Author      : Himal Rai
 * |   Department  : JAVA
 * |   Company     : DIGI Hub
 * |   Created     : 1/5/2026 5:21 PM
 * -------------------------------------------------------------
 */
public class JobJee {
    public static void main(String[] args) throws IOException {
        // URL to scrape
        String url = "https://www.jobejee.com/";

        // Fetch the HTML page
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0") // mimic browser
                .get();

        // Select all job-box-inner divs
        Elements jobBoxes = doc.select("div.job-box-inner");

        for (Element box : jobBoxes) {
            // Get the company name
            Element companyTag = box.selectFirst("a.green-orange-link");
            String company = companyTag != null ? companyTag.text().trim() : "Unknown Company";

            System.out.println("Company: " + company);

            // Get all job titles under sub-title divs
            Elements subTitles = box.select("div.sub-title a");
            for (Element job : subTitles) {
                String jobTitle = job.text().trim();
                String jobUrl = "https://www.jobejee.com" + job.attr("href");
                System.out.println("  - Job: " + jobTitle + " | URL: " + jobUrl);
            }
            System.out.println("----------------------------------------------------");
        }
    }
}
