package com.axreng.backend.infra;

public class AppConfig {
    private final String baseUrl;

    public AppConfig() {
        this.baseUrl = System.getenv("BASE_URL") != null 
        ? System.getenv("BASE_URL") 
        : "https://github.com/arthurgabriel73"; 
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}