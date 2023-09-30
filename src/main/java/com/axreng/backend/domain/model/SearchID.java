package com.axreng.backend.domain.model;

import java.util.regex.Pattern;

import com.axreng.backend.domain.exceptions.ValidationException;

public class SearchID {
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8}$");
    private final String id;

    public SearchID(String id) {
        if (!isValid(id)) {
            throw new ValidationException("SearchID must be an 8-character alphanumeric string.");
        }
        this.id = id;
    }

    public String getValue() {
        return id;
    }

    private boolean isValid(String id) {
        return ALPHANUMERIC_PATTERN.matcher(id).matches();
    }

}