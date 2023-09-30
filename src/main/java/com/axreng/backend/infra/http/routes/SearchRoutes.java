package com.axreng.backend.infra.http.routes;

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axreng.backend.domain.exceptions.DomainException;
import com.axreng.backend.infra.factory.SearchControllerFactory;
import com.axreng.backend.infra.http.controllers.CreateSearchController;
import com.axreng.backend.infra.http.controllers.GetSearchController;
import com.google.gson.Gson;

import static com.axreng.backend.util.AnsiColors.*;

public class SearchRoutes {
    private GetSearchController getSearchController;
    private CreateSearchController createSearchController;
    private Gson gson;

    final Logger logger = LoggerFactory.getLogger(SearchRoutes.class);
    public SearchRoutes() {
        this.getSearchController = SearchControllerFactory.getSearchController();
        this.createSearchController = SearchControllerFactory.createSearchController();
        this.gson = new Gson();

        get("/crawl/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                logger.info("{}Received request to get search with ID: {}{}", CYAN.getCode(), id, RESET.getCode());
                return getSearchController.get(id);
            } catch (DomainException e) {
                logger.error("{}Error while getting search: {}{}", RED.getCode(), e.getMessage(), RESET.getCode());
                res.status(404);
                return gson.toJson("Search not found for ID: " + id);
            }
        });

        post("/crawl", (req, res) -> {
            String requestBody = req.body();
            RequestBodyObject requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
            String keyword = requestBodyObject.keyword;
            int limit = req.queryParams("limit") != null ? Integer.parseInt(req.queryParams("limit")) : 100;
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
