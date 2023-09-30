package com.axreng.backend.infra.http;

import java.util.concurrent.CompletableFuture;

import com.axreng.backend.application.usecase.CreateSearchUseCase;
import com.axreng.backend.domain.dto.SearchDTO;
import com.axreng.backend.domain.model.Search;
import com.google.gson.Gson;

public class CreateSearchController {
    private final CreateSearchUseCase createSearchUseCase;
    private final Gson gson = new Gson();
    
    public CreateSearchController(CreateSearchUseCase createSearchUseCase) {
        this.createSearchUseCase = createSearchUseCase;
    }

    public CompletableFuture<String> create(String keyword, int limit) {
        CompletableFuture<Search> searchFuture = createSearchUseCase.execute(keyword, limit);
        return searchFuture.thenApplyAsync(search -> {
            SearchDTO searchDTO = new SearchDTO(search);
            return gson.toJson(searchDTO);
        });        
    }
    
}