package com.lbranco.tv_tracker_api.provider.tmdb;

import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchMultiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class TmdbClient {

    private final WebClient webClient;
    private final String token;

    public TmdbClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.themoviedb.org/3")
                .build();

        this.token = System.getenv("TMDB_TOKEN");
    }

    public TmdbSearchMultiResponse searchMulti(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("include_adult", false)
                        .queryParam("language", "en-US")
                        .queryParam("page", 1)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbSearchMultiResponse.class)
                .block();
    }
}