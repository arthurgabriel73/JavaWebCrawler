package com.axreng.backend.infra;

public class AppConfig {
    private final String baseUrl;

    public AppConfig() {
        this.baseUrl = System.getenv("BASE_URL"); 
       
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}