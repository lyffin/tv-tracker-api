package com.lbranco.tv_tracker_api.provider.tmdb.mapper;

import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.media.model.MediaDetails;
import com.lbranco.tv_tracker_api.media.model.SeasonDetails;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbMovieDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbSearchResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeasonDetailsResponse;
import com.lbranco.tv_tracker_api.provider.tmdb.dto.TmdbTvSeriesDetailsResponse;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TmdbMapperTest {

    private final TmdbMapper tmdbMapper = new TmdbMapper();

    @Test
    void mapToMediaReturnsEmptyListWhenResponseIsNull() {
        assertThat(tmdbMapper.mapToMedia(null)).isEmpty();
    }

    @Test
    void mapToMediaFiltersUnsupportedResultsAndMapsValidEntries() {
        TmdbSearchResponse.TmdbResult movieResult = new TmdbSearchResponse.TmdbResult();
        movieResult.setId(10);
        movieResult.setTitle("Inception");
        movieResult.setOriginalTitle("Inception");
        movieResult.setPosterPath("/poster.jpg");
        movieResult.setMediaType("movie");
        movieResult.setReleaseDate("2010-07-16");
        movieResult.setVoteAverage(8.8f);

        TmdbSearchResponse.TmdbResult personResult = new TmdbSearchResponse.TmdbResult();
        personResult.setId(99);
        personResult.setMediaType("person");

        TmdbSearchResponse response = new TmdbSearchResponse();
        response.setResults(List.of(movieResult, personResult));

        List<Media> mediaList = tmdbMapper.mapToMedia(response);

        assertThat(mediaList).hasSize(1);
        assertThat(mediaList.getFirst().getId()).isEqualTo(10);
        assertThat(mediaList.getFirst().getType()).isEqualTo(MediaType.MOVIE);
        assertThat(mediaList.getFirst().getReleaseYear()).isEqualTo(2010);
        assertThat(mediaList.getFirst().getScore()).isEqualTo(88.0f);
        assertThat(mediaList.getFirst().getImageUrl()).isEqualTo("https://image.tmdb.org/t/p/w500/poster.jpg");
    }

    @Test
    void mapMovieDetailsNormalizesScoreAndBuildsImageUrl() {
        TmdbMovieDetailsResponse response = new TmdbMovieDetailsResponse();
        response.setId(550);
        response.setTitle("Fight Club");
        response.setOriginalTitle("Fight Club");
        response.setPosterPath("/fight-club.jpg");
        response.setReleaseDate("1999-10-15");
        response.setRuntime(139);
        response.setStatus("Released");
        response.setVoteAverage(8.4f);

        MediaDetails mediaDetails = tmdbMapper.mapMovieDetailsToMediaDetails(response);

        assertThat(mediaDetails.getId()).isEqualTo(550);
        assertThat(mediaDetails.getType()).isEqualTo(MediaType.MOVIE);
        assertThat(mediaDetails.getReleaseYear()).isEqualTo(1999);
        assertThat(mediaDetails.getScore()).isEqualTo(84.0f);
        assertThat(mediaDetails.getImageUrl()).isEqualTo("https://image.tmdb.org/t/p/w500/fight-club.jpg");
    }

    @Test
    void mapTvSeriesToMediaDetailsHandlesMissingRuntimeAndSeasons() {
        TmdbTvSeriesDetailsResponse response = new TmdbTvSeriesDetailsResponse();
        response.setId(1399);
        response.setName("Game of Thrones");
        response.setOriginalName("Game of Thrones");
        response.setFirstAirDate("2011-04-17");
        response.setLastAirDate("2019-05-19");
        response.setVoteAverage(9.2f);
        response.setNumberEpisodes(73);
        response.setStatus("Ended");
        response.setEpisodeRunTime(List.of());
        response.setSeasons(null);

        MediaDetails mediaDetails = tmdbMapper.mapTvSeriesToMediaDetails(response);

        assertThat(mediaDetails.getType()).isEqualTo(MediaType.TV);
        assertThat(mediaDetails.getDuration()).isNull();
        assertThat(mediaDetails.getSeasons()).isEmpty();
        assertThat(mediaDetails.getScore()).isEqualTo(92.0f);
        assertThat(mediaDetails.getReleaseYear()).isEqualTo(2011);
        assertThat(mediaDetails.getEndYear()).isEqualTo(2019);
    }

    @Test
    void mapTvSeasonToSeasonDetailsHandlesMissingImagesAndEpisodes() {
        TmdbTvSeasonDetailsResponse.Episode episodeResponse = new TmdbTvSeasonDetailsResponse.Episode();
        episodeResponse.setId(1001);
        episodeResponse.setEpisodeNumber(1);
        episodeResponse.setName("Winter Is Coming");
        episodeResponse.setStillPath(null);
        episodeResponse.setVoteAverage(7.5f);
        episodeResponse.setAirDate("2011-04-17");
        episodeResponse.setRuntime(62);

        TmdbTvSeasonDetailsResponse response = new TmdbTvSeasonDetailsResponse();
        response.setId(2001);
        response.setAirDate("2011-04-17");
        response.setName("Season 1");
        response.setPosterPath(null);
        response.setSeasonNumber(1);
        response.setVoteAverage(8.1f);
        response.setEpisodes(List.of(episodeResponse));

        SeasonDetails seasonDetails = tmdbMapper.mapTvSeasonToSeasonDetails(response);

        assertThat(seasonDetails.getId()).isEqualTo(2001);
        assertThat(seasonDetails.getImageUrl()).isNull();
        assertThat(seasonDetails.getTotalEpisodes()).isEqualTo(1);
        assertThat(seasonDetails.getScore()).isEqualTo(81.0f);
        assertThat(seasonDetails.getEpisodes()).hasSize(1);
        assertThat(seasonDetails.getEpisodes().getFirst().getImageUrl()).isNull();
        assertThat(seasonDetails.getEpisodes().getFirst().getScore()).isEqualTo(75.0f);
    }
}
