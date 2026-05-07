package com.lbranco.tv_tracker_api.media.service;

import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
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
    private final TmdbService tmdbService;

    public MediaSearchService(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    public List<Media> search(String query, MediaType type) {

        if (type == null) {
            return new ArrayList<>(safeCall("TMDB", query, () -> tmdbService.searchMulti(query)));
        }

        return switch (type) {
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
