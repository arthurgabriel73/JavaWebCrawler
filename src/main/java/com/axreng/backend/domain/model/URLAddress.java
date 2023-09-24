package com.axreng.backend.domain.model;

import java.net.MalformedURLException;
import java.net.URL;

import com.axreng.backend.domain.exceptions.DomainException;

public class URLAddress {
    private final String value;

    public URLAddress(String url) {
        validateURLFormat(url);
        this.value = url;
    }

    public String getValue() {
        return value;
    }

    private void validateURLFormat(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new DomainException("Invalid URL format: " + url);
        }
    }
}