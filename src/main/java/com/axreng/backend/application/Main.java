package com.axreng.backend.application;

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axreng.backend.infra.factory.SearchControllerFactory;
import com.axreng.backend.infra.http.CreateSearchController;
import com.axreng.backend.infra.http.GetSearchController;
import static com.axreng.backend.util.AnsiColors.*;
import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Main.class);
        GetSearchController getSearchController = SearchControllerFactory.getSearchController();
        CreateSearchController createSearchController = SearchControllerFactory.createSearchController();
        Gson gson = new Gson();

        get("/crawl/:id", (req, res) -> {
            String id = req.params(":id");
            logger.info("{}Received request to get search with ID: {}{}", CYAN.getCode(), id, RESET.getCode());
            return getSearchController.get(id);
        });

        post("/crawl", (req, res) -> {
            String requestBody = req.body();
            RequestBodyObject requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
            String keyword = requestBodyObject.keyword;
            int limit = requestBodyObject.limit;
            logger.info("{}Received request to create search with keyword: {} and limit: {}{}", GREEN.getCode(),
                    keyword, limit, RESET.getCode());
            return createSearchController.create(keyword, limit);
        });
    }

    static class RequestBodyObject {
        String keyword;
        int limit;
    }
}
