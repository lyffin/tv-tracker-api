package com.lbranco.tv_tracker_api.provider.trakt;

import com.lbranco.tv_tracker_api.provider.trakt.dto.TraktWatchedShowsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class TraktClient {

    private final RestClient restClient;

    @Value("${trakt.client-id}")
    private String clientId;

    public TraktClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.trakt.tv")
                .build();
    }

    public List<TraktWatchedShowsResponse> getWatchedShows(String accessToken) {
        TraktWatchedShowsResponse[] response = restClient.get()
                .uri("/sync/watched/shows")
                .header("Authorization", "Bearer " + accessToken)
                .header("trakt-api-key", clientId)
                .header("trakt-api-version", "2")
                .header("Content-Type", "application/json")
                .retrieve()
                .body(TraktWatchedShowsResponse[].class);

        return response == null ? List.of() : Arrays.asList(response);
    }
}
