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
public class TmdbTvSeasonDetailsResponse {

    private int _id;

    @JsonProperty("air_date")
    private String airDate;

    private String name;
    private String overview;
    private int id;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("season_number")
    private int seasonNumber;

    @JsonProperty("vote_average")
    private Float voteAverage;

    private List<Episode> episodes;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Episode {

        @JsonProperty("air_date")
        private String airDate;

        @JsonProperty("episode_number")
        private int episodeNumber;

        @JsonProperty("episode_type")
        private String episodeType;

        private int id;
        private String name;
        private String overview;
        private Integer runtime;

        @JsonProperty("season_number")
        private int seasonNumber;

        @JsonProperty("show_id")
        private int showId;

        @JsonProperty("still_path")
        private String stillPath;

        @JsonProperty("vote_average")
        private Float voteAverage;
    }

}
