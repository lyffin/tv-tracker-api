package com.lbranco.tv_tracker_api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Media {

    private String id;
    private Title title;
    private String imageUrl;
    private Type type;
    private Source source;
    private Integer releaseYear;
    private Float score;
    private MediaFormat mediaFormat;

    public enum Type {
        MOVIE, TV, ANIME
    }

    public enum Source {
        IMDB, ANILIST
    }

    public enum MediaFormat {
        TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC, MANGA, NOVEL, ONE_SHOT, UNKNOWN
    }

}
