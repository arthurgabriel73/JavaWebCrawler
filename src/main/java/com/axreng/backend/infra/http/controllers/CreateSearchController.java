package com.axreng.backend.infra.http.controllers;

import com.axreng.backend.application.usecase.CreateSearchUseCase;
import com.axreng.backend.domain.dto.SearchIDDTO;
import com.axreng.backend.domain.exceptions.ApplicationException;
import com.axreng.backend.domain.model.Search;
import com.google.gson.Gson;
import org.slf4j.Logger;

import static com.axreng.backend.util.AnsiColors.*;

public class CreateSearchController {
    private final CreateSearchUseCase createSearchUseCase;
    private final Gson gson;
    private final Logger logger;

    public CreateSearchController(CreateSearchUseCase createSearchUseCase, Gson gson, Logger logger) {
        this.createSearchUseCase = createSearchUseCase;
        this.gson = gson;
        this.logger = logger;
    }

    public String handle(String keyword, int limit) {
        try {
            logger.info("{}Received request to create search with keyword: {} and limit: {}{}", GREEN.getCode(),
                keyword, limit, RESET.getCode());
            Search search = createSearchUseCase.execute(keyword, limit);
            SearchIDDTO searchIDDTO = new SearchIDDTO(search);
            return gson.toJson(searchIDDTO);
         } catch (ApplicationException e) {
            logger.error("{}Error while creating search: {}{}", RED.getCode(), e.getMessage(), RESET.getCode());
            throw e;
        }
    }
}
