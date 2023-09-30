package com.axreng.backend.infra.http.routes;

import static spark.Spark.*;

import com.axreng.backend.domain.exceptions.ApplicationException;
import com.axreng.backend.domain.exceptions.SearchNotFoundException;
import com.axreng.backend.domain.exceptions.ValidationException;
import com.axreng.backend.infra.factory.SearchControllerFactory;
import com.axreng.backend.infra.http.controllers.CreateSearchController;
import com.axreng.backend.infra.http.controllers.GetSearchController;
import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class SearchRoutes {
    private final GetSearchController getSearchController;
    private final CreateSearchController createSearchController;
    private final Gson gson;

    public SearchRoutes() {
        this.getSearchController = SearchControllerFactory.getSearchController();
        this.createSearchController = SearchControllerFactory.createSearchController();
        this.gson = new Gson();

        get("/crawl/:id", this::handleGetRequest);

        post("/crawl", this::handlePostRequest);
    }

    private Object handleGetRequest(Request req, Response res) {
        String id = req.params(":id");
        try {
            return getSearchController.handle(id);
        } catch (SearchNotFoundException e) {
            return handleSearchNotFoundException(res, "Search not found for ID: " + id);
        } catch (ValidationException e) {
            return handleValidationException(res, "Validation error: " + e.getMessage());
        } catch (ApplicationException e) {
            return handleApplicationException(res, "Error while processing request. Please try again later.");
        }
    }

    private Object handlePostRequest(Request req, Response res) {
        String requestBody = req.body();
        RequestBodyObject requestBodyObject;

        try {
            requestBodyObject = gson.fromJson(requestBody, RequestBodyObject.class);
        } catch (Exception e) {
            return handleException(res, 400, "Invalid JSON format in the request body");
        }

        if (requestBodyObject == null || requestBodyObject.keyword == null
                || requestBodyObject.keyword.trim().isEmpty()) {
            return handleException(res, 400, "Keyword must be defined in the request body");
        }

        String keyword = requestBodyObject.keyword;

        try {
            int limit = req.queryParams("limit") != null 
            ? Integer.parseInt(req.queryParams("limit")) 
            : 100;
            return createSearchController.handle(keyword, limit);
        } catch (NumberFormatException e) {
            return handleException(res, 400, "Invalid value for 'limit' in the query");
        } catch (ValidationException e) {
            return handleValidationException(res, "Validation error: " + e.getMessage());
        } catch (ApplicationException e) {
            return handleApplicationException(res, "Error while processing request. Please try again later.");
        }
    }

    private Object handleSearchNotFoundException(Response res, String message) {
        res.status(404);
        return gson.toJson(message);
    }

    private Object handleValidationException(Response res, String message) {
        res.status(400);
        return gson.toJson(message);
    }

    private Object handleApplicationException(Response res, String message) {
        res.status(500);
        return gson.toJson(message);
    }

    private Object handleException(Response res, int statusCode, String message) {
        res.status(statusCode);
        return gson.toJson(message);
    }

    static class RequestBodyObject {
        String keyword;
        int limit;
    }
}
