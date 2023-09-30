package com.axreng.backend.application;

import static spark.Spark.*;

import com.axreng.backend.infra.factory.SearchControllerFactory;
import com.axreng.backend.infra.http.CreateSearchController;
import com.axreng.backend.infra.http.GetSearchController;
import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        GetSearchController getSearchController = SearchControllerFactory.getSearchController();
        CreateSearchController createSearchController = SearchControllerFactory.createSearchController();
        Gson gson = new Gson();

        get("/crawl/:id", (req, res) -> {
            String id = req.params(":id");
            return getSearchController.get(id);
        });

        post("/crawl", (req, res) -> {
            String requestBody = req.body();
            RequestBodyObject requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
            String keyword = requestBodyObject.keyword;
            int limit = requestBodyObject.limit;
            return createSearchController.create(keyword, limit);
        });
    }

    static class RequestBodyObject {
        String keyword;
        int limit;
    }
}
