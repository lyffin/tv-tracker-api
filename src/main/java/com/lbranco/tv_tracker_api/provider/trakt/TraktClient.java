package com.lbranco.tv_tracker_api.provider.trakt;

import com.lbranco.tv_tracker_api.provider.trakt.dto.TraktWatchedShowsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class TraktClient {

    private final WebClient webClient;

    @Value("${trakt.client-id}")
    private String clientId;

    public TraktClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.trakt.tv")
                .build();
    }

    public List<TraktWatchedShowsResponse> getWatchedShows(String accessToken) {
        return webClient.get()
                .uri("/sync/watched/shows")
                .header("Authorization", "Bearer " + accessToken)
                .header("trakt-api-key", clientId)
                .header("trakt-api-version", "2")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToFlux(TraktWatchedShowsResponse.class)
                .collectList()
                .blockOptional()
                .orElse(List.of());
    }
}
