package com.axreng.backend.application.usecase;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.domain.exceptions.SearchNotFoundException;
import com.axreng.backend.domain.model.*;

public class GetSearchUseCase {
    private SearchRepository repository;

    public GetSearchUseCase(SearchRepository repository ) {
        this.repository = repository;
    }
    
    public Search execute(String id) {
        SearchID searchID = new SearchID(id);
        Search search = repository.find(searchID);
        if (search == null) {
            throw new SearchNotFoundException("Search not found for ID: " + id);
        }
        return search;
    }
}
