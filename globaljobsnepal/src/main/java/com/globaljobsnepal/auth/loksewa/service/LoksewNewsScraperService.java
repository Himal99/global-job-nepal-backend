package com.globaljobsnepal.auth.loksewa.service;

import com.globaljobsnepal.auth.loksewa.dto.LoksewaNotice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoksewNewsScraperService {

    private static final String URL =
            "https://psc.gov.np/category/notice-advertisement/all";

    public List<LoksewaNotice> scrapeNotices() throws Exception {

        Document doc = Jsoup
                .connect(URL)
                .timeout(15_000)
                .userAgent("Mozilla/5.0")
                .get();

        List<LoksewaNotice> notices = new ArrayList<>();

        Elements rows = doc.select("table tr");

        for (Element row : rows) {

            Element link = row.selectFirst("td a[href]");
            if (link == null) continue;

            String pdfUrl = link.absUrl("href");

            // text is inside <div> within <a>
            Element titleDiv = link.selectFirst("div");
            String title = titleDiv != null
                    ? titleDiv.text().trim()
                    : link.text().trim();

            Element img = link.selectFirst("img");
            String iconUrl = img != null
                    ? img.absUrl("src")
                    : null;

            notices.add(new LoksewaNotice(title, pdfUrl, iconUrl));
        }

        return notices;
    }

    public static void main(String[] args) throws Exception {

        LoksewNewsScraperService service = new LoksewNewsScraperService();
        List<LoksewaNotice> notices = service.scrapeNotices();

        notices.forEach(n -> {
            System.out.println("Title : " + n.getTitle());
            System.out.println("PDF   : " + n.getPdfUrl());
            System.out.println("----------------------------------");
        });
    }
}
