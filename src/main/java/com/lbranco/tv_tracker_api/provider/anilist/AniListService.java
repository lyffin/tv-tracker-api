package com.lbranco.tv_tracker_api.provider.anilist;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.provider.anilist.mapper.AniListMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AniListService {

    private final AniListClient aniListClient;
    private final AniListMapper aniListMapper;

    public AniListService(AniListClient aniListClient, AniListMapper aniListMapper) {
        this.aniListClient = aniListClient;
        this.aniListMapper = aniListMapper;
    }

    public List<Media> searchAnime(String query) {
        var response = aniListClient.searchAnime(query);
        return aniListMapper.mapToMedia(response);
    }
}
