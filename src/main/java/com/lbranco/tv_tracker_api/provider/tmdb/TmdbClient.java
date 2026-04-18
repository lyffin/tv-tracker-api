package com.lbranco.tv_tracker_api.provider.tmdb;

import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeasonDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TmdbClient {

    private final WebClient webClient;
    private final String token;

    public TmdbClient(WebClient.Builder builder,
                      @Value("${tmdb.token:}") String token) {
        this.webClient = builder
                .baseUrl("https://api.themoviedb.org/3")
                .build();
        this.token = token;
    }

    public TmdbSearchResponse searchMulti(String query) {
        validateToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("language", "en-US")
                        .queryParam("page", 1)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbSearchResponse.class)
                .block();
    }

    public TmdbSearchResponse searchMovie(String query) {
        validateToken();
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search/movie")
                                .queryParam("query", query)
                                .queryParam("language", "en-US")
                                .queryParam("page", 1)
                                .build())
                        .header("Authorization", "Bearer " + token)
                        .header("accept", "application/json")
                        .retrieve()
                        .bodyToMono(TmdbSearchResponse.class)
                        .block();
    }

    public TmdbSearchResponse searchTv(String query) {
        validateToken();
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search/tv")
                                .queryParam("query", query)
                                .queryParam("language", "en-US")
                                .queryParam("page", 1)
                                .build())
                        .header("Authorization", "Bearer " + token)
                        .header("accept", "application/json")
                        .retrieve()
                        .bodyToMono(TmdbSearchResponse.class)
                        .block();
    }

    public TmdbMovieDetailsResponse movieDetails(int id) {
        validateToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("language", "en-US")
                        .build(id))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbMovieDetailsResponse.class)
                .block();
    }

    public TmdbTvSeriesDetailsResponse tvSeriesDetails(int id) {
        validateToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{id}")
                        .queryParam("language", "en-US")
                        .build(id))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbTvSeriesDetailsResponse.class)
                .block();
    }

    public TmdbTvSeasonDetailsResponse tvSeasonDetails(int seriesId, int seasonNum) {
        validateToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{series_id}/season/{season_number}")
                        .queryParam("language", "en-US")
                        .build(seriesId, seasonNum))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbTvSeasonDetailsResponse.class)
                .block();
    }

    private void validateToken() {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("TMDB token is missing. Set TMDB_TOKEN before calling TMDB endpoints.");
        }
    }
}
