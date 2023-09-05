package com.desckapg.eolbot.external;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EOLService {
    private static final String API_URL = "https://endoflife.date/api/";

    private final HttpClient apiClient;
    private final Gson gson;

    private final Logger logger;

    public EOLService() {
        this.gson = new Gson();
        this.apiClient = HttpClient.newHttpClient();
        this.logger = LogManager.getLogger("EOL_API");
    }

    public CompletableFuture<List<String>> getAllTechnologies() {
        return sendGet("all.json").thenApply(json ->
                gson.fromJson(json, new TypeToken<>() {
                }.getType())
        );
    }

    private CompletableFuture<String> sendGet(String method) {
        logger.info("Call {} API method from {}", method, API_URL);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(API_URL + method))
                .build();
        CompletableFuture<HttpResponse<String>> responseAsync =
                apiClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return responseAsync.thenApply(HttpResponse::body);
    }

}
