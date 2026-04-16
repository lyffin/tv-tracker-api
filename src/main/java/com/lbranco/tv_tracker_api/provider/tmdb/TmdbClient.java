package com.lbranco.tv_tracker_api.provider.tmdb;

import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    public TmdbSearchResponse searchMulti(String query) {
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
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie")
                        .queryParam("movie_id", id)
                        .queryParam("language", "en-US")
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbMovieDetailsResponse.class)
                .block();
    }

    public TmdbTvSeriesDetailsResponse tvSeriesDetails(int id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv")
                        .queryParam("series_id", id)
                        .queryParam("language", "en-US")
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbTvSeriesDetailsResponse.class)
                .block();
    }
}