package com.axreng.backend.application.usecase;

import java.util.concurrent.CompletableFuture;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.domain.model.*;

public class CreateSearchUseCase {
    private SearchRepository repository;
    private CrawlingService crawlingService;

    public CreateSearchUseCase(SearchRepository repository, CrawlingService crawlingService) {
        this.repository = repository;
        this.crawlingService = crawlingService;
    }
    
    public Search execute(String keyword, int limit) {
        Search search = new Search(new Keyword(keyword));
        try {
            CompletableFuture<Void> crawlCompletion = new CompletableFuture<>();
            crawlingService.crawl(keyword, limit, url -> {
            search.addURL(new URLAddress(url));
            repository.upsert(search);
        }).thenAccept(result -> crawlCompletion.complete(null));
        crawlCompletion.get();
        search.markAsDone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return search;
    }
}
