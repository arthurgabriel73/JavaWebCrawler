package com.axreng.backend.infra.factory;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.application.usecase.CreateSearchUseCase;
import com.axreng.backend.application.usecase.GetSearchUseCase;
import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.infra.AppConfig;
import com.axreng.backend.infra.DefaultWebCrawler;
import com.axreng.backend.infra.http.CreateSearchController;
import com.axreng.backend.infra.http.GetSearchController;
import com.axreng.backend.infra.repository.InMemorySearchRepository;

public class SearchControllerFactory {

    private SearchControllerFactory() {}

    static int numThreads = Runtime.getRuntime().availableProcessors();
    static CrawlingService crawlingService = new DefaultWebCrawler(new AppConfig().getBaseUrl(), numThreads);
    static SearchRepository repository = new InMemorySearchRepository();
    static CreateSearchUseCase createSearchUseCase = new CreateSearchUseCase(repository, crawlingService);
    static GetSearchUseCase getSearchUseCase = new GetSearchUseCase(repository);

    public static GetSearchController getSearchController() {
        return new GetSearchController(getSearchUseCase);
    }

    public static CreateSearchController createSearchController() {
        return new CreateSearchController(createSearchUseCase);
    }
}