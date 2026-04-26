package com.lbranco.tv_tracker_api.provider.trakt;

import com.lbranco.tv_tracker_api.provider.trakt.dto.TraktWatchedShowsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraktService {

    private final TraktClient traktClient;

    public TraktService(TraktClient traktClient) {
        this.traktClient = traktClient;
    }

    public List<TraktWatchedShowsResponse> getWatchedShows(String accessToken) {
        return traktClient.getWatchedShows(accessToken);
    }
}
