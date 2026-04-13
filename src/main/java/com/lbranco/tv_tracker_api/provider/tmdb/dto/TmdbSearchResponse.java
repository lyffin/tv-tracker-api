package com.lbranco.tv_tracker_api.provider.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbSearchResponse {

    private Integer page;
    private List<TmdbResult> results;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TmdbResult {

        private Integer id;
        private String title;
        private String name;

        @JsonProperty("original_title")
        private String originalTitle;

        @JsonProperty("original_name")
        private String originalName;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("media_type")
        private String mediaType;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("first_air_date")
        private String firstAirDate;

        @JsonProperty("vote_average")
        private Float voteAverage;
    }
}
