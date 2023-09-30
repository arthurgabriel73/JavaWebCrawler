package com.axreng.backend.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.axreng.backend.util.AnsiColors.*;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String DEFAULT_BASE_URL = "https://github.com/arthurgabriel73";
    private String baseUrl;

    public AppConfig() {
        this.baseUrl = loadPropertiesOrDefault();
    }

    private String loadPropertiesOrDefault() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                logger.error("{}Sorry, unable to find application.properties, using default base url", RED.getCode());
                return DEFAULT_BASE_URL;
            }
            prop.load(input);
            return prop.getProperty("default.BASE_URL", DEFAULT_BASE_URL);
        } catch (IOException ex) {
            logger.error("{}Error loading application properties: {}", RED.getCode(), ex.getMessage());
            return DEFAULT_BASE_URL;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}