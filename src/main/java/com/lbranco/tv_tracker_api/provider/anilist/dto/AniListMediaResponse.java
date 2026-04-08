package com.lbranco.tv_tracker_api.provider.anilist.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AniListMediaResponse {

    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {
        @JsonProperty("Page")
        private Page page;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Page {
        private List<Media> media;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Media {
        private int id;
        private Title title;
        private Integer averageScore;
        private CoverImage coverImage;
        private StartDate startDate;
        @JsonProperty("format")
        private MediaFormat mediaFormat;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Title {
        private String romaji;
        private String english;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CoverImage {
        private String large;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StartDate {
        private Integer year;
    }

    public enum MediaFormat {
        TV,
        TV_SHORT,
        MOVIE,
        SPECIAL,
        OVA,
        ONA,
        MUSIC,
        MANGA,
        NOVEL,
        ONE_SHOT,
        @JsonEnumDefaultValue
        UNKNOWN
    }
}
