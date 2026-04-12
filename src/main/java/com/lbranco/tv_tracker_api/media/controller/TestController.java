package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.provider.anilist.AniListClient;
import com.lbranco.tv_tracker_api.provider.anilist.dto.AniListMediaResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.TmdbClient;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchMultiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/tmdb")
public class TestController {

    private final TmdbClient tmdbClient;

    public TestController(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    @GetMapping("/search")
    public TmdbSearchMultiResponse search(@RequestParam String query) {
        return tmdbClient.searchMulti(query);
    }
}