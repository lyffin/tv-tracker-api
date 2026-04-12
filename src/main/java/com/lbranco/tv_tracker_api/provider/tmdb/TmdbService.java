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

    public List<Media> search(String query) {
        return tmdbMapper.mapToMedia(
                tmdbClient.searchMulti(query)
        );
    }
}
