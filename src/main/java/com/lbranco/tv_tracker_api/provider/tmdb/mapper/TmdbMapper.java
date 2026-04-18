package com.lbranco.tv_tracker_api.provider.tmdb.mapper;

import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.media.model.MediaDetails;
import com.lbranco.tv_tracker_api.media.model.SeasonDetails;
import com.lbranco.tv_tracker_api.media.model.Title;
import com.lbranco.tv_tracker_api.shared.enums.MediaSource;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeasonDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TmdbMapper {

    private static final String IMAGE_BASE = "https://image.tmdb.org/t/p/w500";

    public List<Media> mapToMedia(TmdbSearchResponse response) {
        if (response == null || response.getResults() == null) {
            return List.of();
        }

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
        media.setSource(MediaSource.TMDB);
        media.setReleaseYear(extractYear(tmdbResult.getReleaseDate() != null ? tmdbResult.getReleaseDate() : tmdbResult.getFirstAirDate()));
        media.setScore(
                tmdbResult.getVoteAverage() != null
                        ? tmdbResult.getVoteAverage() * 10
                        : null
        );

        return media;
    }

    public MediaDetails mapMovieDetailsToMediaDetails(TmdbMovieDetailsResponse response) {

        MediaDetails mediaDetails = new MediaDetails();

        mediaDetails.setId("tmdb:" + response.getId());

        Title title = new Title();
        title.setEnglish(response.getTitle() != null ? response.getTitle() : response.getOriginalTitle());
        title.setOriginal(response.getOriginalTitle() != null
                ? response.getOriginalTitle()
                : response.getTitle());
        mediaDetails.setTitle(title);

        mediaDetails.setImageUrl(
                response.getPosterPath() != null
                        ? IMAGE_BASE + response.getPosterPath()
                        : null
        );
        mediaDetails.setDescription(response.getOverview());
        mediaDetails.setReleaseYear(extractYear(response.getReleaseDate()));
        mediaDetails.setDuration(response.getRuntime());
        mediaDetails.setStatus(response.getStatus());
        mediaDetails.setScore(normalizeScore(response.getVoteAverage()));
        mediaDetails.setType(MediaType.MOVIE);
        mediaDetails.setSource(MediaSource.TMDB);

        return mediaDetails;
    }

    public MediaDetails mapTvSeriesToMediaDetails(TmdbTvSeriesDetailsResponse response) {

        MediaDetails mediaDetails = new MediaDetails();

        mediaDetails.setId("tmdb:" + response.getId());

        Title title = new Title();
        title.setEnglish(response.getName() != null ? response.getName() : response.getOriginalName());
        title.setOriginal(response.getOriginalName() != null
                ? response.getOriginalName()
                : response.getName());
        mediaDetails.setTitle(title);

        mediaDetails.setImageUrl(
                response.getPosterPath() != null
                        ? IMAGE_BASE + response.getPosterPath()
                        : null
        );

        mediaDetails.setType(MediaType.TV);
        mediaDetails.setSource(MediaSource.TMDB);
        mediaDetails.setReleaseYear(extractYear(response.getFirstAirDate()));
        mediaDetails.setEndYear(extractYear(response.getLastAirDate()));
        mediaDetails.setScore(normalizeScore(response.getVoteAverage()));
        mediaDetails.setDescription(response.getOverview());
        mediaDetails.setDuration(firstOrNull(response.getEpisodeRunTime()));
        mediaDetails.setTotalEpisodes(response.getNumberEpisodes());
        mediaDetails.setStatus(response.getStatus());

        List<MediaDetails.Season> seasons = safeList(response.getSeasons())
                .stream()
                .map(this::mapToSingleSeason)
                .collect(Collectors.toList());

        mediaDetails.setSeasons(seasons);

        return mediaDetails;
    }

    private MediaDetails.Season mapToSingleSeason(TmdbTvSeriesDetailsResponse.Season tmdbSeason) {

        MediaDetails.Season season = new MediaDetails.Season();

        season.setId(tmdbSeason.getId());
        season.setTotalEpisodes(tmdbSeason.getEpisodeCount());
        season.setImageUrl(tmdbSeason.getPosterPath()!= null
                ? IMAGE_BASE + tmdbSeason.getPosterPath()
                : null
        );

        season.setReleaseYear(extractYear(tmdbSeason.getAirDate()));
        season.setTitle(tmdbSeason.getName());
        season.setDescription(tmdbSeason.getOverview());
        season.setScore(normalizeScore(tmdbSeason.getVoteAverage()));
        season.setSeasonNumber(tmdbSeason.getSeasonNumber());

        return season;
    }

    public SeasonDetails mapTvSeasonToSeasonDetails(TmdbTvSeasonDetailsResponse response) {

        SeasonDetails seasonDetails = new SeasonDetails();

        seasonDetails.setId(response.getId());
        seasonDetails.setTotalEpisodes(safeList(response.getEpisodes()).size());
        seasonDetails.setImageUrl(buildImageUrl(response.getPosterPath()));
        seasonDetails.setReleaseYear(extractYear(response.getAirDate()));
        seasonDetails.setTitle(response.getName());
        seasonDetails.setDescription(response.getOverview());
        seasonDetails.setScore(normalizeScore(response.getVoteAverage()));
        seasonDetails.setSeasonNumber(response.getSeasonNumber());

        List<SeasonDetails.Episode> episodes = safeList(response.getEpisodes())
                .stream()
                .map(this::mapToSingleEpisode)
                .collect(Collectors.toList());

        seasonDetails.setEpisodes(episodes);

        return seasonDetails;
    }

    private SeasonDetails.Episode mapToSingleEpisode(TmdbTvSeasonDetailsResponse.Episode tmdbEpisode) {

        SeasonDetails.Episode episode = new SeasonDetails.Episode();

        episode.setId(tmdbEpisode.getId());
        episode.setEpisodeNumber(tmdbEpisode.getEpisodeNumber());
        episode.setTitle(tmdbEpisode.getName());
        episode.setImageUrl(buildImageUrl(tmdbEpisode.getStillPath()));
        episode.setScore(normalizeScore(tmdbEpisode.getVoteAverage()));
        episode.setDescription(tmdbEpisode.getOverview());
        episode.setReleaseDate(tmdbEpisode.getAirDate());
        episode.setDuration(tmdbEpisode.getRuntime());

        return episode;
    }

    private boolean isValidMedia(TmdbSearchResponse.TmdbResult result) {
        String type = result.getMediaType();
        return "movie".equalsIgnoreCase(type) || "tv".equalsIgnoreCase(type);
    }

    private MediaType mapType(String mediaType) {
        if (mediaType == null) return null;

        return switch (mediaType.toLowerCase()) {
            case "movie" -> MediaType.MOVIE;
            case "tv" -> MediaType.TV;
            default -> null;
        };
    }

    private Integer extractYear(String date) {

        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(date.substring(0, 4));
        } catch (Exception e) {
            return null;
        }
    }

    private String buildImageUrl(String path) {
        return path == null || path.isBlank() ? null : IMAGE_BASE + path;
    }

    private Float normalizeScore(Float score) {
        return score == null ? null : score * 10;
    }

    private Integer firstOrNull(List<Integer> values) {
        return (values == null || values.isEmpty()) ? null : values.getFirst();
    }

    private <T> List<T> safeList(List<T> values) {
        return values == null ? Collections.emptyList() : values;
    }
}
