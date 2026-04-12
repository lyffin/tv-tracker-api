package com.lbranco.tv_tracker_api.media.service;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.provider.anilist.AniListService;
import com.lbranco.tv_tracker_api.provider.tmdb.TmdbService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaSearchService {

    private final AniListService aniListService;
    private final TmdbService tmdbService;

    public MediaSearchService(AniListService aniListService,
                              TmdbService tmdbService) {
        this.aniListService = aniListService;
        this.tmdbService = tmdbService;
    }

    public List<Media> search(String query) {

        List<Media> results = new ArrayList<>();

        try {
            results.addAll(aniListService.searchAnime(query));
        } catch (Exception e) {
            System.out.println("AniList failed: " + e.getMessage());
        }

        try {
            results.addAll(tmdbService.search(query));
        } catch (Exception e) {
            System.out.println("TMDB failed: " + e.getMessage());
        }

        return results;
    }
}
