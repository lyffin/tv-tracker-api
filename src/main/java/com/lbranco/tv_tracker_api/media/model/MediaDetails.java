package com.lbranco.tv_tracker_api.media.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lbranco.tv_tracker_api.shared.enums.MediaFormat;
import com.lbranco.tv_tracker_api.shared.enums.MediaSource;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MediaDetails {

    private String id;
    private Title title;
    private String imageUrl;
    private MediaType type;
    private MediaSource source;
    private Integer releaseYear;
    private Integer endYear;
    private Float score;
    private String description;
    private Integer duration;
    private List<Season> seasons;
    private Integer totalEpisodes;
    private MediaFormat mediaFormat;
    private String status;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Season {

        private int id;
        private Integer totalEpisodes;
        private String imageUrl;
        private Integer releaseYear;
        private String title;
        private String description;
        private Float score;
        private Integer seasonNumber;
    }
}
