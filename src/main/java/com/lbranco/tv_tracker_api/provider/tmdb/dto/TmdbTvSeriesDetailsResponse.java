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
public class TmdbTvSeriesDetailsResponse {

    private int id;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("first_air_date")
    private String firstAirDate;

    @JsonProperty("episode_run_time")
    private List<Integer> episodeRunTime;

    @JsonProperty("in_production")
    private Boolean inProduction;

    @JsonProperty("last_air_date")
    private String lastAirDate;

    private String name;

    @JsonProperty("next_episode_to_air")
    private String nextEpisodeToAIr;

    @JsonProperty("number_of_episodes")
    private Integer numberEpisodes;

    @JsonProperty("original_name")
    private String originalName;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("seasons")
    private List<Season> seasons;

    private String status;

    @JsonProperty("vote_average")
    private Float voteAverage;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Season {

        @JsonProperty("air_date")
        private String airDate;

        @JsonProperty("episode_count")
        private int episodeCount;

        private int id;
        private String name;
        private String overview;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("season_number")
        private int seasonNumber;

        @JsonProperty("vote_average")
        private Float voteAverage;
    }
}
