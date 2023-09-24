package com.axreng.backend.domain.model;

import java.util.List;
import java.util.ArrayList;

import com.axreng.backend.util.IDGenerator;

public class Search {
    private SearchID id;
    private Keyword keyword;
    private SearchStatus status;
    private List<URLAddress> urls;

    public Search(Keyword keyword) {
        this.id = generateSearchID();
        this.keyword = keyword;
        this.status = SearchStatus.ACTIVE;
        this.urls = new ArrayList<>();
    }

    public SearchID getId() {
        return id;
    }

    public void setId(SearchID id) {
        this.id = id;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public SearchStatus getStatus() {
        return status;
    }

    public void setStatus(SearchStatus status) {
        this.status = status;
    }

    public List<URLAddress> getUrls() {
        return urls;
    }

    public void setUrls(List<URLAddress> urls) {
        this.urls = urls;
    }

    private SearchID generateSearchID() {
        return new SearchID(IDGenerator.generateID());
    }

    public void markAsDone() {
        this.status = SearchStatus.DONE;
    }

    public void addURL(URLAddress url) {
        urls.add(url);
    }

}