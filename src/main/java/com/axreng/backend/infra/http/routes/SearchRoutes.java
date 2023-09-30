package com.axreng.backend.infra.http.routes;

import static spark.Spark.*;

import com.axreng.backend.domain.exceptions.ApplicationException;
import com.axreng.backend.domain.exceptions.SearchNotFoundException;
import com.axreng.backend.domain.exceptions.ValidationException;
import com.axreng.backend.infra.factory.SearchControllerFactory;
import com.axreng.backend.infra.http.controllers.CreateSearchController;
import com.axreng.backend.infra.http.controllers.GetSearchController;
import com.google.gson.Gson;

public class SearchRoutes {
    private final GetSearchController getSearchController;
    private final CreateSearchController createSearchController;
    private final Gson gson;

    public SearchRoutes() {
        this.getSearchController = SearchControllerFactory.getSearchController();
        this.createSearchController = SearchControllerFactory.createSearchController();
        this.gson = new Gson();

        get("/crawl/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                return getSearchController.handle(id);
            } catch (SearchNotFoundException e) {
                res.status(404);
                return gson.toJson("Search not found for ID: " + id);
            } catch (ValidationException e) {
                res.status(400);
                return gson.toJson("Validation error: " + e.getMessage());
            } catch (ApplicationException e) {
                res.status(500);
                return gson.toJson("Error while processing request. Please try again later.");
            }
        });

        post("/crawl", (req, res) -> {
            String requestBody = req.body();
            RequestBodyObject requestBodyObject;

            try {
                requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
            } catch (Exception e) {
                res.status(400);
                return gson.toJson("Invalid JSON format in the request body");
            }

            if (requestBodyObject == null || requestBodyObject.keyword == null || requestBodyObject.keyword.trim().isEmpty()) {
                res.status(400);
                return gson.toJson("Keyword must be defined in the request body");
            }

            String keyword = requestBodyObject.keyword;

            try {
                int limit = req.queryParams("limit") != null ? Integer.parseInt(req.queryParams("limit")) : 100;
                return createSearchController.handle(keyword, limit);
            } catch (NumberFormatException e) {
                res.status(400);
                return gson.toJson("Invalid value for 'limit' in the query");
            } catch (ValidationException e) {
                res.status(400);
                return gson.toJson("Validation error: " + e.getMessage());
            } catch (ApplicationException e) {
                res.status(500);
                return gson.toJson("Error while processing request. Please try again later.");
            }
        });
    }

    static class RequestBodyObject {
        String keyword;
        int limit;
    }
}
