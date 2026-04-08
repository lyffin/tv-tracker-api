package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.provider.anilist.AniListClient;
import com.lbranco.tv_tracker_api.provider.anilist.dto.AniListMediaResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final AniListClient client;

    public TestController(AniListClient client) {
        this.client = client;
    }

    @GetMapping
    public AniListMediaResponse test() {
        return client.searchAnime("Demon slayer");
    }
}