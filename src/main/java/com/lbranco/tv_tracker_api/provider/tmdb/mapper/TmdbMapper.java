package com.lbranco.tv_tracker_api.provider.tmdb.mapper;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.model.Title;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TmdbMapper {

    private static final String IMAGE_BASE = "https://image.tmdb.org/t/p/w500";

    public List<Media> mapToMedia(TmdbSearchResponse response) {
        return response.getResults()
                .stream()
                .filter(this::isValidMedia)
                .map(this::mapToSingleMedia)
                .collect(Collectors.toList());
    }

    private Media mapToSingleMedia(TmdbSearchResponse.TmdbResult tmdbResult) {

        Media media = new Media();

        media.setId("tmdb:" + tmdbResult.getId());

        Title title = new Title();
        title.setEnglish(tmdbResult.getTitle() != null ? tmdbResult.getTitle() : tmdbResult.getName());
        title.setOriginal(tmdbResult.getOriginalTitle() != null
                ? tmdbResult.getOriginalTitle()
                : tmdbResult.getOriginalName());
        media.setTitle(title);

        media.setImageUrl(
                tmdbResult.getPosterPath() != null
                        ? IMAGE_BASE + tmdbResult.getPosterPath()
                        : null
        );
        media.setType(mapType(tmdbResult.getMediaType()));
        media.setSource(Media.Source.TMDB);
        media.setReleaseYear(extractYear(tmdbResult));
        media.setScore(
                tmdbResult.getVoteAverage() != null
                        ? tmdbResult.getVoteAverage() * 10
                        : null
        );

        return media;
    }

    private boolean isValidMedia(TmdbSearchResponse.TmdbResult result) {
        String type = result.getMediaType();
        return "movie".equalsIgnoreCase(type) || "tv".equalsIgnoreCase(type);
    }

    private Media.Type mapType(String mediaType) {
        if (mediaType == null) return null;

        return switch (mediaType.toLowerCase()) {
            case "movie" -> Media.Type.MOVIE;
            case "tv" -> Media.Type.TV;
            default -> null;
        };
    }

    private Integer extractYear(TmdbSearchResponse.TmdbResult result) {
        String date = result.getReleaseDate() != null
                ? result.getReleaseDate()
                : result.getFirstAirDate();

        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(date.substring(0, 4));
        } catch (Exception e) {
            return null;
        }
    }
}
