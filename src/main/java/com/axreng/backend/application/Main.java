package com.axreng.backend.application;

import static spark.Spark.*;

import com.axreng.backend.infra.AppConfig;
import com.axreng.backend.infra.WebCrawler;
import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        get("/crawl/:id", (req, res) -> {
            return "Procurando...";
        });

        post("/crawl", (req, res) -> {
            String requestBody = req.body();
            Gson gson = new Gson();
            RequestBodyObject requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
            String keyword = requestBodyObject.keyword;
            int limit = requestBodyObject.limit;
            WebCrawler webCrawler = new WebCrawler(new AppConfig().getBaseUrl());
            try {
                webCrawler.crawl(keyword, limit);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Crawling...";
        });
    }

    static class RequestBodyObject {
        String keyword;
        int limit;
    }

}

