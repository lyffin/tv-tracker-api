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
public class AniListMediaDetailsResponse {

    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data {

        @JsonProperty("Media")
        private Media media;
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
        private Status status;
        private EndDate endDate;
        private String description;
        private Integer duration;
        private Integer episodes;
        private List<StreamingEpisode> streamingEpisodes;
        private Relations relations;
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class EndDate {
        private Integer year;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StreamingEpisode {
        private String thumbnail;
        private String title;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Relations {
        private List<Edge> edges;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Edge {
        private RelationType relationType;
        private Node node;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Node {
        private int id;
        private CoverImage coverImage;
        private Integer averageScore;
        private StartDate startDate;
        @JsonProperty("format")
        private MediaFormat mediaFormat;
        private Title title;
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

    public enum RelationType {
        ADAPTATION,
        PREQUEL,
        SEQUEL,
        PARENT,
        SIDE_STORY,
        CHARACTER,
        SUMMARY,
        ALTERNATIVE,
        SPIN_OFF,
        OTHER,
        SOURCE,
        COMPILATION,
        CONTAINS,
        @JsonEnumDefaultValue
        UNKNOWN
    }

    public enum Status {
        FINISHED,
        RELEASING,
        NOT_YET_RELEASED,
        CANCELLED,
        HIATUS,
        @JsonEnumDefaultValue
        UNKNOWN
    }
}
