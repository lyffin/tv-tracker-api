package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.media.service.MediaDetailsService;
import com.lbranco.tv_tracker_api.media.service.MediaSearchService;
import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.model.MediaDetails;
import com.lbranco.tv_tracker_api.provider.anilist.AniListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaSearchService mediaSearchService;
    private final MediaDetailsService mediaDetailsService;

    public MediaController(MediaSearchService mediaSearchService,
                           MediaDetailsService mediaDetailsService) {

        this.mediaSearchService = mediaSearchService;
        this.mediaDetailsService = mediaDetailsService;
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
}
