package com.example.MovieRank.Services.Movie.HttpRequestClass;

import com.example.MovieRank.Exceptions.RequestErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestClass {

    static Logger logger = LoggerFactory.getLogger(HttpRequestClass.class);

    public static HttpResponse<String> sendRequest(String path) {

        try {
            HttpClient httpClient = java.net.http.HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(path))
                    .build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            logger.error("The query to the movie database could not be completed");
            throw new RequestErrorException("Nie można zrealizować zapytania do bazy filmów!");
        }
    }
}
