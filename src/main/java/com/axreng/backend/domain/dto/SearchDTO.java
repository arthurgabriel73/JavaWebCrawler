package com.axreng.backend.domain.dto;

import java.util.List;

import com.axreng.backend.domain.model.Search;
import com.axreng.backend.domain.model.URLAddress;

public class SearchDTO {
    private final String id;
    private final String keyword;
    private final String status;
    private final List<String> urls;

    public SearchDTO(Search search) {
        this.id = search.getId().getValue();
        this.keyword = search.getKeyword().getValue();
        this.status = search.getStatus().getValue();
        this.urls = search.getUrls().stream().map(URLAddress::getValue).toList();
    }

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getUrls() {
        return urls;
    }
}