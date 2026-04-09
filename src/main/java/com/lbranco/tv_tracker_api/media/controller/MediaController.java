package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.provider.anilist.AniListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final AniListService aniListService;

    public MediaController(AniListService aniListService) {
        this.aniListService = aniListService;
    }

    @GetMapping("/search")
    public List<Media> search(@RequestParam String query) {
        return aniListService.searchAnime(query);
    }
}
