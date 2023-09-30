package com.axreng.backend.util;

public enum AnsiColors {
    RESET("\u001B[0m"),
    GREEN("\u001B[32m"),
    CYAN("\u001B[36m");

    private final String code;

    AnsiColors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}