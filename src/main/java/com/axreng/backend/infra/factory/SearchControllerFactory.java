package com.axreng.backend.infra.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.application.usecase.CreateSearchUseCase;
import com.axreng.backend.application.usecase.GetSearchUseCase;
import com.axreng.backend.infra.http.controllers.CreateSearchController;
import com.axreng.backend.infra.http.controllers.GetSearchController;
import com.axreng.backend.infra.repository.InMemorySearchRepository;
import com.google.gson.Gson;

public class SearchControllerFactory {

    private SearchControllerFactory() {}

    static Gson gson = new Gson();
    static final Logger logger = LoggerFactory.getLogger(SearchControllerFactory.class);
    static SearchRepository repository = new InMemorySearchRepository();
    static CreateSearchUseCase createSearchUseCase = new CreateSearchUseCase(repository);
    static GetSearchUseCase getSearchUseCase = new GetSearchUseCase(repository);

    public static GetSearchController getSearchController() {
        return new GetSearchController(getSearchUseCase, gson, logger);
    }

    public static CreateSearchController createSearchController() {
        return new CreateSearchController(createSearchUseCase, gson, logger);
    }
}