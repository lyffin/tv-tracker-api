package com.lbranco.tv_tracker_api.media.service;

import com.lbranco.tv_tracker_api.model.MediaDetails;
import com.lbranco.tv_tracker_api.provider.tmdb.TmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class MediaDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MediaDetailsService.class);
    private final TmdbService tmdbService;

    public MediaDetailsService(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    public MediaDetails details(MediaDetails.Type type, int id) {

        return switch (type) {
            case MOVIE -> safeCall("TMDB-MOVIE", id, () -> tmdbService.movieDetails(id));
            case TV -> safeCall("TMDB-TV", id, () -> tmdbService.tvSeriesDetails(id));
            case ANIME -> throw new UnsupportedOperationException("Anime not implemented yet");
        };
    }

    private MediaDetails safeCall(String source, int id, Supplier<MediaDetails> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("{} details failed for id={}", source, id, e);
            throw new RuntimeException(source + " failed for id=" + id, e);
        }
    }
}
