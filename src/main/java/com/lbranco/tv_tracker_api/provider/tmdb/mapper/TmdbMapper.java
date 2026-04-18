package com.lbranco.tv_tracker_api.provider.tmdb.mapper;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.model.MediaDetails;
import com.lbranco.tv_tracker_api.model.SeasonDetails;
import com.lbranco.tv_tracker_api.model.Title;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeasonDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
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
        mediaDetails.setScore(response.getVoteAverage());
        mediaDetails.setType(MediaDetails.Type.MOVIE);
        mediaDetails.setSource(MediaDetails.Source.TMDB);

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

        mediaDetails.setType(MediaDetails.Type.TV);
        mediaDetails.setSource(MediaDetails.Source.TMDB);
        mediaDetails.setReleaseYear(extractYear(response.getFirstAirDate()));
        mediaDetails.setEndYear(extractYear(response.getLastAirDate()));
        mediaDetails.setScore(response.getVoteAverage());
        mediaDetails.setDescription(response.getOverview());
        mediaDetails.setDuration(response.getEpisodeRunTime().getFirst());
        mediaDetails.setTotalEpisodes(response.getNumberEpisodes());
        mediaDetails.setStatus(response.getStatus());

        List<MediaDetails.Season> seasons = response.getSeasons()
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
        season.setScore(tmdbSeason.getVoteAverage());
        season.setSeasonNumber(tmdbSeason.getSeasonNumber());

        return season;
    }

    public SeasonDetails mapTvSeasonToSeasonDetails(TmdbTvSeasonDetailsResponse response) {

        SeasonDetails seasonDetails = new SeasonDetails();

        seasonDetails.setId(response.getId());
        seasonDetails.setTotalEpisodes(response.getEpisodes().size());
        seasonDetails.setImageUrl(IMAGE_BASE + response.getPosterPath());
        seasonDetails.setReleaseYear(extractYear(response.getAirDate()));
        seasonDetails.setTitle(response.getName());
        seasonDetails.setDescription(response.getOverview());
        seasonDetails.setScore(response.getVoteAverage());
        seasonDetails.setSeasonNumber(response.getSeasonNumber());

        List<SeasonDetails.Episode> episodes = response.getEpisodes()
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
        episode.setImageUrl(IMAGE_BASE + tmdbEpisode.getStillPath());
        episode.setScore(tmdbEpisode.getVoteAverage());
        episode.setDescription(tmdbEpisode.getOverview());
        episode.setReleaseDate(tmdbEpisode.getAirDate());
        episode.setDuration(tmdbEpisode.getRuntime());

        return episode;
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
}
