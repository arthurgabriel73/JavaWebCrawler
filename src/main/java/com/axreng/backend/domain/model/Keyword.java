package com.axreng.backend.domain.model;

import com.axreng.backend.domain.exceptions.ValidationException;

public class Keyword {
    private final String value;

    public Keyword(String keyword) {
        if (keyword.length() < 4 || keyword.length() > 32) {
            throw new ValidationException("Keyword must be between 4 and 32 characters.");
        }
        this.value = keyword;
    }

    public String getValue() {
        return value;
    }
}