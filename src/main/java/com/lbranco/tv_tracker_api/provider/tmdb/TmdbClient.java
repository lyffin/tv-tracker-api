package com.lbranco.tv_tracker_api.provider.tmdb;

import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeasonDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TmdbClient {

    private final RestClient restClient;
    private final String token;

    public TmdbClient(RestClient.Builder builder,
                      @Value("${tmdb.token:}") String token) {
        this.restClient = builder
                .baseUrl("https://api.themoviedb.org/3")
                .build();
        this.token = token;
    }

    public TmdbSearchResponse searchMulti(String query) {
        validateToken();
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("language", "en-US")
                        .queryParam("page", 1)
                        .build())
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .body(TmdbSearchResponse.class);
    }

    public TmdbSearchResponse searchMovie(String query) {
        validateToken();
        return restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search/movie")
                                .queryParam("query", query)
                                .queryParam("language", "en-US")
                                .queryParam("page", 1)
                                .build())
                        .header("Authorization", "Bearer " + token)
                        .header("accept", "application/json")
                        .retrieve()
                        .body(TmdbSearchResponse.class);
    }

    public TmdbSearchResponse searchTv(String query) {
        validateToken();
        return restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search/tv")
                                .queryParam("query", query)
                                .queryParam("language", "en-US")
                                .queryParam("page", 1)
                                .build())
                        .header("Authorization", "Bearer " + token)
                        .header("accept", "application/json")
                        .retrieve()
                        .body(TmdbSearchResponse.class);
    }

    public TmdbMovieDetailsResponse movieDetails(int id) {
        validateToken();
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("language", "en-US")
                        .build(id))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .body(TmdbMovieDetailsResponse.class);
    }

    public TmdbTvSeriesDetailsResponse tvSeriesDetails(int id) {
        validateToken();
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{id}")
                        .queryParam("language", "en-US")
                        .build(id))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .body(TmdbTvSeriesDetailsResponse.class);
    }

    public TmdbTvSeasonDetailsResponse tvSeasonDetails(int seriesId, int seasonNum) {
        validateToken();
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{series_id}/season/{season_number}")
                        .queryParam("language", "en-US")
                        .build(seriesId, seasonNum))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .retrieve()
                .body(TmdbTvSeasonDetailsResponse.class);
    }

    private void validateToken() {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("TMDB token is missing. Set TMDB_TOKEN before calling TMDB endpoints.");
        }
    }
}
