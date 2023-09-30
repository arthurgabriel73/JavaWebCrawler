package com.axreng.backend.application.usecase;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.domain.model.Keyword;
import com.axreng.backend.domain.model.Search;
import com.axreng.backend.domain.model.URLAddress;
import com.axreng.backend.infra.AppConfig;
import com.axreng.backend.infra.DefaultWebCrawler;

import java.util.concurrent.CompletableFuture;

public class CreateSearchUseCase {
    private final SearchRepository repository;
    private CrawlingService crawlingService;

    public CreateSearchUseCase(SearchRepository repository) {
        this.repository = repository;
        int numThreads = Runtime.getRuntime().availableProcessors();
        crawlingService = new DefaultWebCrawler(new AppConfig().getBaseUrl(), numThreads);
    }

    public Search execute(String keyword, int limit) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        crawlingService = new DefaultWebCrawler(new AppConfig().getBaseUrl(), numThreads);
        Search search = new Search(new Keyword(keyword));
        CompletableFuture<Void> crawlCompletion = crawlingService.crawl(keyword, limit, url -> {
            search.addURL(new URLAddress(url));
            System.out.println(url);
            repository.upsert(search);
        });
        crawlCompletion.thenApplyAsync(result -> {
            search.markAsDone();
            return search;
        });
        return search;
    }
}
