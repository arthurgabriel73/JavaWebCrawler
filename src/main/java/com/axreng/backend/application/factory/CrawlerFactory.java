package com.axreng.backend.application.factory;

import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.infra.AppConfig;
import com.axreng.backend.infra.DefaultWebCrawler;

public class CrawlerFactory {
    private CrawlerFactory() {}

    public static CrawlingService getCrawler() {
        int numThreads = Runtime.getRuntime().availableProcessors();
        return new DefaultWebCrawler(new AppConfig().getBaseUrl(), numThreads);
    }
}
