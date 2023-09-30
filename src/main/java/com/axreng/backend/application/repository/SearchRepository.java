package com.axreng.backend.application.repository;

import com.axreng.backend.domain.model.Search;
import com.axreng.backend.domain.model.SearchID;

public interface SearchRepository {
    public Search upsert(Search search);
    public Search find(SearchID id);

}
