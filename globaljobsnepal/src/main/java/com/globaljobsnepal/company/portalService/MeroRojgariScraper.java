package com.globaljobsnepal.company.portalService;

import com.globaljobsnepal.dto.JobResponseDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeroRojgariScraper {

    public List<JobResponseDto> getJobs() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--disable-gpu"); // Optional, often needed
        options.addArguments("--window-size=1920,1080"); // Optional, helps rendering

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://merorojgari.com/");

        // Find all job listings
        List<WebElement> jobs = driver.findElements(By.cssSelector("ul.job_listings > li.job_listing"));
        List<JobResponseDto> jobResponseDtos = new ArrayList<>();
        for (WebElement job : jobs) {
            String url = job.findElement(By.cssSelector("a")).getAttribute("href");
            String title = job.findElement(By.cssSelector(".position h3")).getText();
            String company = job.findElement(By.cssSelector(".company strong")).getText();
            String location = job.findElement(By.cssSelector(".location")).getText();
            String postedDate = job.findElement(By.cssSelector(".meta .date time")).getAttribute("datetime");
            
            // Some jobs have application deadline
            String deadline = "";
            List<WebElement> deadlineElems = job.findElements(By.cssSelector(".meta .application-deadline"));
            if (!deadlineElems.isEmpty()) {
                deadline = deadlineElems.get(0).getText().replace("Closes:", "").trim();
            }

            jobResponseDtos.add(
                    JobResponseDto
                            .builder()
                            .jobTitle(title)
                            .applyBefore("")
                            .postedAt(postedDate)
                            .location(location)
                            .companyName(company)
                            .url(url)
                            .build()
            );

            }

        System.out.println(jobResponseDtos);

        driver.quit();

        return jobResponseDtos;
    }
}
