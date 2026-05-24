package com.lbranco.tv_tracker_api.media.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SeasonDetails {

    private int id;
    private Integer totalEpisodes;
    private String imageUrl;
    private Integer releaseYear;
    private String title;
    private String description;
    private Float score;
    private Integer seasonNumber;
    private List<Episode> episodes;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Episode {

        private int id;
        private Integer episodeNumber;
        private String title;
        private String imageUrl;
        private Float score;
        private String description;
        private String releaseDate;
        private Integer duration;
    }
}
