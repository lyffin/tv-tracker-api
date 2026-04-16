package com.lbranco.tv_tracker_api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MediaDetails {

    private String id;
    private Title title;
    private String imageUrl;
    private Type type;
    private Source source;
    private Integer releaseYear;
    private Integer endYear;
    private Float score;
    private String description;
    private Integer duration;
    private List<Season> seasons;
    private Integer totalEpisodes;
    private MediaFormat mediaFormat;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Season {

        private Integer totalEpisodes;
        private String imageUrl;
        private Integer releaseYear;
        private String title;
        private String description;
        private Float score;
        private Integer seasonNumber;
    }

    public enum Type {
        MOVIE, TV, ANIME
    }

    public enum Source {
        TMDB, ANILIST
    }

    public enum MediaFormat {
        TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC, MANGA, NOVEL, ONE_SHOT, UNKNOWN
    }
}
