package com.axreng.backend.application.usecase;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.domain.model.Keyword;
import com.axreng.backend.domain.model.Search;
import com.axreng.backend.domain.model.URLAddress;

import java.util.concurrent.CompletableFuture;

public class CreateSearchUseCase {
    private final SearchRepository repository;
    private final CrawlingService crawlingService;

    public CreateSearchUseCase(SearchRepository repository, CrawlingService crawlingService) {
        this.repository = repository;
        this.crawlingService = crawlingService;
    }

    public Search execute(String keyword, int limit) {
        Search search = new Search(new Keyword(keyword));
        CompletableFuture<Void> crawlCompletion = crawlingService.crawl(keyword, limit, url -> {
            search.addURL(new URLAddress(url));
            repository.upsert(search);
        });
        crawlCompletion.thenApplyAsync(result -> {
            search.markAsDone();
            return search;
        });
        return search;
    }
}
