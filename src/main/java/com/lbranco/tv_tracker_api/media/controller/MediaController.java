package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.media.service.MediaDetailsService;
import com.lbranco.tv_tracker_api.media.service.MediaSearchService;
import com.lbranco.tv_tracker_api.media.service.SeasonDetailsService;
import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.model.MediaDetails;
import com.lbranco.tv_tracker_api.model.SeasonDetails;
import com.lbranco.tv_tracker_api.provider.anilist.AniListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaSearchService mediaSearchService;
    private final MediaDetailsService mediaDetailsService;
    private final SeasonDetailsService seasonDetailsService;

    public MediaController(MediaSearchService mediaSearchService,
                           MediaDetailsService mediaDetailsService,
                           SeasonDetailsService seasonDetailsService) {

        this.mediaSearchService = mediaSearchService;
        this.mediaDetailsService = mediaDetailsService;
        this.seasonDetailsService = seasonDetailsService;
    }

    @GetMapping("/search")
    public List<Media> search(
            @RequestParam String query,
            @RequestParam(required = false) Media.Type type
    ) {
        return mediaSearchService.search(query, type);
    }

    @GetMapping("/{type}/{id}")
    public MediaDetails details(
            @PathVariable String type,
            @PathVariable int id
    ) {
        return mediaDetailsService.details(
                MediaDetails.Type.valueOf(type.toUpperCase()), id);
    }

    @GetMapping("/tv/{seriesId}/season/{seasonNum}")
    public SeasonDetails seasonDetails(
            @PathVariable int seriesId,
            @PathVariable int seasonNum

    ) {
        return seasonDetailsService.seasonDetails(seriesId, seasonNum);
    }
}
