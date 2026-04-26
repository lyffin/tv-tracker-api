package com.lbranco.tv_tracker_api.provider.trakt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraktWatchedShowsResponse {

    private Show show;

    @Getter
    @Setter
    public static class Show {
        private String title;
        private Ids ids;
    }

    @Getter
    @Setter
    public static class Ids {
        private Integer tmdb;
    }
}
