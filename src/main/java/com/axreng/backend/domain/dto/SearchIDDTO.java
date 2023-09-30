package com.axreng.backend.domain.dto;

import com.axreng.backend.domain.model.Search;

public class SearchIDDTO {
    private final String id;


    public SearchIDDTO(Search search) {
        this.id = search.getId().getValue();
    }

    public String getId() {
        return id;
    }

}
