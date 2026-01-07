package com.globaljobsnepal.company.portalService;

import com.globaljobsnepal.dto.JobResponseDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MerojobFullScraper {

    public List<JobResponseDto> getJobs() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--disable-gpu"); // Optional, often needed
        options.addArguments("--window-size=1920,1080");


        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://merojob.com");

        // wait until company job cards load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.rounded-lg.bg-card")
        ));

        List<WebElement> cards = driver.findElements(
                By.cssSelector("div.rounded-lg.bg-card")
        );

        String mainWindow = driver.getWindowHandle();
        List<JobResponseDto> jobsList = new ArrayList<>();
        for (WebElement card : cards) {

            String companyName = getTextSafe(card, By.cssSelector("h3.text-neutral-800"));
            String logoUrl = getAttrSafe(card, By.cssSelector("img"), "src");

            List<WebElement> jobs = card.findElements(By.cssSelector("ul li a"));


            for (WebElement job : jobs) {

                String jobTitle = job.getText().trim();
                String jobUrl = job.getAttribute("href");

                // open job detail in new tab
                ((JavascriptExecutor) driver)
                        .executeScript("window.open(arguments[0], '_blank');", jobUrl);

                // switch to new tab
                for (String window : driver.getWindowHandles()) {
                    if (!window.equals(mainWindow)) {
                        driver.switchTo().window(window);
                        break;
                    }
                }

                 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                        "div.flex.flex-col.xl\\:flex-row.flex-wrap.items-start.xl\\:items-center.gap-2.text-gray-700.text-sm"
                )));

                // Get all job cards
                WebElement element = driver.findElement(By.cssSelector(
                        "div.flex.flex-col.xl\\:flex-row.flex-wrap.items-start.xl\\:items-center.gap-2.text-gray-700.text-sm"
                ));



                String location = "Not found";

                WebElement locationContainer = element.findElement(By.cssSelector("div.flex.items-center.gap-2"));

                // find the span **following the <p> with the SVG**
                WebElement locationSpan = locationContainer.findElement(
                        By.xpath("./span[1]") // first span after the <p>
                );

                location = locationSpan.getText();

                // wait for job title
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h1.text-xl, h1.text-2xl")
                ));

                /* ===============================
                   SCRAPE JOB DETAIL PAGE
                   =============================== */

                String detailJobTitle = getTextSafe(driver,
                        By.cssSelector("h1.text-xl, h1.text-2xl"));

                String publishedOn = getTextSafe(driver,
                        By.xpath("//div[contains(text(),'Published on')]"));

                String category = getTextSafe(driver,
                        By.xpath("//svg[contains(@class,'briefcase')]/ancestor::div[1]//span"));

                String level = getTextSafe(driver,
                        By.xpath("//svg[contains(@class,'building')]/ancestor::div[1]//div[contains(@class,'text-sm')]"));

                String employmentType = getTextSafe(driver,
                        By.xpath("//svg[contains(@class,'clock')]/ancestor::div[1]//p[last()]"));

                String vacancy = getTextSafe(driver,
                        By.xpath("//span[contains(text(),'Vacancy')]"));


                String experience = getTextSafe(driver,
                        By.xpath("//span[contains(text(),'Experience')]"));

                String salary = getTextSafe(driver,
                        By.xpath("//span[contains(text(),'Offered Salary')]/ancestor::div//span[last()]"));

                String jobDescription = getTextSafe(driver,
                        By.xpath("//p[text()='Job Description']/ancestor::div[1]/following-sibling::div"));

                String applyBefore = getTextSafe(driver,
                        By.xpath("//p[contains(text(),'Apply Before')]/span"));

                /* ===============================
                   JSON OUTPUT
                   =============================== */

                String json = String.format(
                        "{ \"jobTitle\":\"%s\", \"companyName\":\"%s\", \"location\":\"%s\", " +
                                "\"experience\":\"%s\", \"salary\":\"%s\", \"applyBefore\":\"%s\", \"jobUrl\":\"%s\" }",
                        escape(detailJobTitle),
                        escape(companyName),
                        escape(location),
                        escape(experience),
                        escape(salary),
                        escape(applyBefore),
                        escape(jobDescription),
                        escape(publishedOn),
                        jobUrl
                );

                System.out.println(json);
                jobsList
                        .add(JobResponseDto
                                .builder()
                                .jobTitle(jobTitle)
                                .companyName(companyName)
                                .location(location)
                                .url(jobUrl)
                                .createdAt(publishedOn)
                                .jobDescription(jobDescription)
                                .applyBefore(applyBefore)
                                .build());

                // close job detail tab
                driver.close();
                driver.switchTo().window(mainWindow);
            }
        }

        driver.quit();

        return jobsList;
    }

    /* ===============================
       HELPER METHODS
       =============================== */

    private static String getTextSafe(WebDriver driver, By by) {
        try {
            return driver.findElement(by).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getTextSafe(WebElement parent, By by) {
        try {
            return parent.findElement(by).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getAttrSafe(WebElement parent, By by, String attr) {
        try {
            return parent.findElement(by).getAttribute(attr);
        } catch (Exception e) {
            return null;
        }
    }

    private static String escape(String text) {
        if (text == null) return null;
        return text.replace("\"", "\\\"").replace("\n", " ");
    }
}
