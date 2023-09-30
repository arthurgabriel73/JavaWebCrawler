package com.axreng.backend.infra.http;

import com.axreng.backend.application.usecase.GetSearchUseCase;
import com.axreng.backend.domain.dto.SearchDTO;
import com.axreng.backend.domain.model.Search;
import com.google.gson.Gson;

public class GetSearchController {
    private final GetSearchUseCase getSearchUseCase;
    private final Gson gson = new Gson();
    
    public GetSearchController(GetSearchUseCase getSearchUseCase) {
        this.getSearchUseCase = getSearchUseCase;
    }

    public String get(String id) {
        Search search = getSearchUseCase.execute(id);
        SearchDTO searchDTO = new SearchDTO(search);
        return gson.toJson(searchDTO);
    }
    
}