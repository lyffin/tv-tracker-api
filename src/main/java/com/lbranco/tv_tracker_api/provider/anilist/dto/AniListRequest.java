package com.lbranco.tv_tracker_api.provider.anilist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AniListRequest {
    private String query;
    private Variables variables;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Variables {
        private String search;
    }
}
