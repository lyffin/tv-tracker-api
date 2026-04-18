package com.lbranco.tv_tracker_api.media.service;

import com.lbranco.tv_tracker_api.model.SeasonDetails;
import com.lbranco.tv_tracker_api.provider.tmdb.TmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class SeasonDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SeasonDetailsService.class);
    private final TmdbService tmdbService;

    public SeasonDetailsService(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    public SeasonDetails seasonDetails(int seriesId, int seasonNum) {

        return safeCall(seriesId, seasonNum, () -> tmdbService.tvSeasonDetails(seriesId, seasonNum));
    }

    private SeasonDetails safeCall(int seriesId, int seasonNum,
                                   Supplier<SeasonDetails> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("TMDB-Season details failed for seriesId={} and seasonNum={}",
                    seriesId, seasonNum, e);
            throw new RuntimeException("TMDB-Season failed for seasonNum=" + seasonNum, e);
        }
    }
}
