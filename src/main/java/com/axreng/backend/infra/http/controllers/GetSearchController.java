package com.axreng.backend.infra.http.controllers;

import com.axreng.backend.application.usecase.GetSearchUseCase;
import com.axreng.backend.domain.dto.SearchDTO;
import com.axreng.backend.domain.exceptions.ApplicationException;
import com.axreng.backend.domain.model.Search;
import com.google.gson.Gson;
import org.slf4j.Logger;

import static com.axreng.backend.util.AnsiColors.*;

public class GetSearchController {
    private final GetSearchUseCase getSearchUseCase;
    private final Gson gson;
    private final Logger logger;

    public GetSearchController(GetSearchUseCase getSearchUseCase, Gson gson, Logger logger) {
        this.getSearchUseCase = getSearchUseCase;
        this.gson = gson;
        this.logger = logger;
    }

    public String handle(String id) {
        try {
            logger.info("{}Received request to get search with ID: {}{}", CYAN.getCode(), id, RESET.getCode());
            Search search = getSearchUseCase.execute(id);
            SearchDTO searchDTO = new SearchDTO(search);
            return gson.toJson(searchDTO);
        } catch (ApplicationException e) {
            logger.error("{}Error while getting search: {}{}", RED.getCode(), e.getMessage(), RESET.getCode());
            throw e;
        }
    }
}