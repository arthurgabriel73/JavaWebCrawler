package com.axreng.backend.domain.model;

import com.axreng.backend.domain.exceptions.ValidationException;

public enum SearchStatus {
    ACTIVE("active"), DONE("done");

    private final String value;

    private SearchStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SearchStatus fromValue(String value) {
        for (SearchStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new ValidationException("Invalid SearchStatus value: " + value);
    }
}