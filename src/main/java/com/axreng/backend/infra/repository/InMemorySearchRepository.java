package com.axreng.backend.infra.repository;

import com.axreng.backend.application.repository.SearchRepository;
import com.axreng.backend.domain.exceptions.DomainException;
import com.axreng.backend.domain.model.Search;
import com.axreng.backend.domain.model.SearchID;

import java.util.HashMap;
import java.util.Map;

public class InMemorySearchRepository implements SearchRepository {
    private final Map<String, Search> searches = new HashMap<>();

    @Override
    public Search upsert(Search search) {
        searches.put(search.getId().getValue(), search);
        return search;
    }

    @Override
    public Search find(SearchID id) {
        Search search = searches.get(id.getValue());
        return search;
    }

}
