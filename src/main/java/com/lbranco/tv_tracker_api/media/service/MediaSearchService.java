package com.lbranco.tv_tracker_api.media.service;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.provider.anilist.AniListService;
import com.lbranco.tv_tracker_api.provider.tmdb.TmdbService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MediaSearchService {

    private static final Logger log = LoggerFactory.getLogger(MediaSearchService.class);
    private final AniListService aniListService;
    private final TmdbService tmdbService;

    public MediaSearchService(AniListService aniListService,
                              TmdbService tmdbService) {
        this.aniListService = aniListService;
        this.tmdbService = tmdbService;
    }

    public List<Media> search(String query, Media.Type type) {

        if (type == null) {
            List<Media> results = new ArrayList<>();

            results.addAll(safeCall("AniList", query, () -> aniListService.searchAnime(query)));
            results.addAll(safeCall("TMDB", query, () -> tmdbService.searchMulti(query)));

            return results;
        }

        return switch (type) {
            case ANIME -> safeCall("AniList", query, () -> aniListService.searchAnime(query));
            case MOVIE -> safeCall("TMDB-MOVIE", query, () -> tmdbService.searchMovie(query));
            case TV -> safeCall("TMDB-TV", query, () -> tmdbService.searchTv(query));
        };
    }

    private List<Media> safeCall(String source, String query, Supplier<List<Media>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("{} search failed for query={}", source, query, e);
            return List.of();
        }
    }
}
