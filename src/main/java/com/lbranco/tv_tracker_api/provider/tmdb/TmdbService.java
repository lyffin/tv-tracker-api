package com.lbranco.tv_tracker_api.provider.tmdb;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.provider.tmdb.mapper.TmdbMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmdbService {
    private final TmdbClient tmdbClient;
    private final TmdbMapper tmdbMapper;

    public TmdbService(TmdbClient tmdbClient, TmdbMapper tmdbMapper) {
        this.tmdbClient = tmdbClient;
        this.tmdbMapper = tmdbMapper;
    }

    public List<Media> searchMulti(String query) {
        return tmdbMapper.mapToMedia(
                tmdbClient.searchMulti(query)
        );
    }

    public List<Media> searchMovie(String query) {
        return tmdbMapper.mapToMedia(
                tmdbClient.searchMovie(query)
        );
    }

    public List<Media> searchTv(String query) {
        return tmdbMapper.mapToMedia(
                tmdbClient.searchTv(query)
        );
    }
}
