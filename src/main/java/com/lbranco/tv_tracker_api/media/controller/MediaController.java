package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.shared.exception.InvalidMediaTypeException;
import com.lbranco.tv_tracker_api.media.service.MediaDetailsService;
import com.lbranco.tv_tracker_api.media.service.MediaSearchService;
import com.lbranco.tv_tracker_api.media.service.SeasonDetailsService;
import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.media.model.MediaDetails;
import com.lbranco.tv_tracker_api.media.model.SeasonDetails;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
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
            @RequestParam(required = false) MediaType type
    ) {
        return mediaSearchService.search(query, type);
    }

    @GetMapping("/{type}/{id}")
    public MediaDetails details(
            @PathVariable String type,
            @PathVariable int id
    ) {
        return mediaDetailsService.details(parseMediaType(type), id);
    }

    @GetMapping("/tv/{seriesId}/season/{seasonNum}")
    public SeasonDetails seasonDetails(
            @PathVariable int seriesId,
            @PathVariable int seasonNum

    ) {
        return seasonDetailsService.seasonDetails(seriesId, seasonNum);
    }

    private MediaType parseMediaType(String type) {
        try {
            return MediaType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidMediaTypeException(type);
        }
    }
}
